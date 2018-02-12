package me.vit.maven.migrate.version.apply;

import me.vit.maven.exception.execute.VersionApplierException;
import me.vit.maven.migrate.options.DestinationOptions;
import me.vit.maven.migrate.version.Version;
import me.vit.maven.migrate.version.factory.JsonFactory;
import me.vit.maven.shell.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class VersionApplier {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionApplier.class);
    private static final Pattern SUCCESS_WRITE_RESULT_PATTERN =
            Pattern.compile("(?s)^WriteResult\\(\\{ \"nInserted\" : [0-9]+ \\}\\).*");
    private Shell shell;
    private DestinationOptions dstOptions;
    private JsonFactory jsonFactory;

    public VersionApplier(Shell shell, DestinationOptions dstOptions) {
        this.shell = shell;
        this.dstOptions = dstOptions;
        this.jsonFactory = new JsonFactory(
                new JsonFactory.FieldNames(
                        dstOptions.getNumberField(),
                        dstOptions.getNumberPartsField(),
                        dstOptions.getNameField()
                )
        );
    }

    public void apply(Version version) {
        LOGGER.debug("  └ applying version: {}", version);
        String query = String.format("db.getCollection('%s').insert(%s)",
                dstOptions.getCollection(),
                jsonFactory.versionJson(version)
        );
        String output = shell.eval(query);
        validateApplyResult(output);
    }

    private static void validateApplyResult(String output) {
        boolean success = isWriteSucceeded(output);
        if (success) {
            LOGGER.debug("  └ version applied successfully");
        } else {
            LOGGER.error("  └ version apply failed");
            throw new VersionApplierException("error while applying version; output is: " + output);
        }
    }

    static boolean isWriteSucceeded(String writeOutput) {
        return SUCCESS_WRITE_RESULT_PATTERN.matcher(writeOutput).matches();
    }
}
