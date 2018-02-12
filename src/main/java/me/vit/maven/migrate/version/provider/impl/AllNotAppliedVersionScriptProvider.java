package me.vit.maven.migrate.version.provider.impl;

import me.vit.maven.migrate.version.Version;
import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.migrate.version.VersionSorter;
import me.vit.maven.migrate.version.reader.VersionDocumentReader;
import me.vit.maven.migrate.version.reader.VersionScriptReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class AllNotAppliedVersionScriptProvider extends AbstractVersionScriptProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllAfterLastAppliedVersionScriptProvider.class);
    private VersionDocumentReader documentReader;

    public AllNotAppliedVersionScriptProvider(VersionScriptReader scriptReader, VersionDocumentReader documentReader) {
        super(scriptReader);
        this.documentReader = documentReader;
    }

    @Override
    protected List<VersionScript> filter(List<VersionScript> availableVersionScripts) {
        List<? extends Version> versionDocuments = documentReader.getAll();
        VersionSorter.sortAscending(versionDocuments);
        LOGGER.debug("  └ ({}) version documents found", versionDocuments.size());

        Iterator<VersionScript> it = availableVersionScripts.listIterator();
        while (it.hasNext()) {
            VersionScript versionScript = it.next();
            if (versionDocuments.contains(versionScript)) {
                LOGGER.debug("  └ excluding script {} as applied yet", versionScript.getNumber());
                it.remove();
            } else {
                LOGGER.debug("  └ including script {} as not applied yet", versionScript.getNumber());
            }
        }
        return availableVersionScripts;
    }
}
