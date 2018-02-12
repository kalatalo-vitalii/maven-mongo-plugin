package me.vit.maven.migrate.version.reader.impl;

import me.vit.maven.exception.execute.VersionDocumentReaderException;
import me.vit.maven.migrate.options.DestinationOptions;
import me.vit.maven.migrate.version.VersionDocument;
import me.vit.maven.migrate.version.reader.VersionDocumentReader;
import me.vit.maven.shell.Shell;
import me.vit.maven.shell.ShellQueryBuilder;
import me.vit.maven.util.StringUtil;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ShellVersionDocumentReader implements VersionDocumentReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShellVersionDocumentReader.class);
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("'ISODate(\"'yyyy-MM-dd'T'HH:mm:ss.S'Z\")'");
    private static final String DOCUMENTS_EDGES_REGEX = "(?s)\\}.{1,2}\\{";
    private Shell shell;
    private DestinationOptions dstOptions;

    public ShellVersionDocumentReader(Shell shell, DestinationOptions dstOptions) {
        this.shell = shell;
        this.dstOptions = dstOptions;
    }

    @Override
    public VersionDocument getLastVersion() {
        int versionNumberPartsMaxSize = getVersionNumberPartsMaxSize();
        LOGGER.debug("  └ version number parts max size: {}", versionNumberPartsMaxSize);
        if (versionNumberPartsMaxSize == 0) {
            return null;
        }
        LOGGER.debug("  └ getting last version..");
        String query = ShellQueryBuilder.buildMaxArrayValuesQuery(
                dstOptions.getCollection(),
                dstOptions.getNumberPartsField(),
                versionNumberPartsMaxSize
        );
        String output = shell.eval(query);
        return StringUtil.isNotBlank(output) ? parseVersionDocument(output) : null;
    }

    private Integer getVersionNumberPartsMaxSize() {
        LOGGER.debug("  └ getting version number parts max size..");
        String outputDocumentFieldName = "size";
        String query = ShellQueryBuilder.buildMaxArraySizeQuery(
                dstOptions.getCollection(),
                dstOptions.getNumberPartsField(),
                outputDocumentFieldName
        );
        String output = shell.eval(query);
        if (StringUtil.isBlank(output)) {
            LOGGER.debug("    └ no versions found");
            return 0;
        } else {
            return getJsonFieldValue(output, outputDocumentFieldName);
        }
    }

    @Override
    public List<VersionDocument> getAll() {
        LOGGER.debug("  └ getting all the version documents..");
        String query = ShellQueryBuilder.buildFindAllQuery(dstOptions.getCollection());
        String output = shell.eval(query);
        return parseVersionDocuments(output);
    }

    private List<VersionDocument> parseVersionDocuments(String documents) {
        if (StringUtil.isBlank(documents)) {
            return Collections.emptyList();
        }
        String documentsSeparator = "#";
        String[] documentArray = documents
                .replaceAll(DOCUMENTS_EDGES_REGEX, "}" + documentsSeparator + "{")
                .split(documentsSeparator);
        if (documentArray.length == 0) {
            return Collections.emptyList();
        }
        List<VersionDocument> versionDocuments = new ArrayList<>();
        for (String document : documentArray) {
            versionDocuments.add(parseVersionDocument(document));
        }
        return versionDocuments;
    }

    private VersionDocument parseVersionDocument(String document) {
        String id = getJsonFieldValue(document, "_id");
        String number = getJsonFieldValue(document, dstOptions.getNumberField());
        JSONArray jsonArray = getJsonFieldValue(document, dstOptions.getNumberPartsField());
        int[] numberParts = parseNumberParts(jsonArray);
        String name = getJsonFieldValue(document, dstOptions.getNameField());
        String timestamp = getJsonFieldValue(document, "timestamp");

        return new VersionDocument(number, numberParts, name, id, parseDate(timestamp));
    }

    private static int[] parseNumberParts(JSONArray jsonArray) {
        Integer[] array = jsonArray.toArray(new Integer[]{});
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    static Date parseDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            return DATE_FORMATTER.parse(dateString);

        } catch (ParseException e) {
            throw new VersionDocumentReaderException("can't parse date: " + dateString, e);
        }
    }

    static <T> T getJsonFieldValue(String json, String fieldName) {
        try {
            return JsonPath.read(json, String.format("$.%s", fieldName));

        } catch (InvalidJsonException e) {
            LOGGER.error("  └ invalid json: {}", json);
            throw new VersionDocumentReaderException(String.format("can't read document: %s", e.getMessage()), e);

        } catch (Exception e) {
            LOGGER.error("  └ unknown error while reading json: {}", json);
            throw new VersionDocumentReaderException(String.format("can't read document: %s", e.getMessage()), e);
        }
    }
}
