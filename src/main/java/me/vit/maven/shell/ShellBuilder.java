package me.vit.maven.shell;

import me.vit.maven.shell.options.ShellOptions;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

public class ShellBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShellBuilder.class);
    private String shellHome;
    private ShellOptions options;

    public ShellBuilder home(String shellHome) {
        this.shellHome = shellHome;
        return this;
    }

    public ShellBuilder options(ShellOptions options) {
        this.options = options;
        return this;
    }

    public Shell build() {
        String shellPath = buildShellPath(shellHome);
        return new Shell(shellPath, options);
    }

    private static String buildShellPath(String shellHome) {
        if (StringUtils.isEmpty(shellHome)) {
            LOGGER.debug("  └ Mongo shell home is not specified: using shell in PATH");
            return "mongo";

        } else {
            LOGGER.debug("  └ using shell home for Mongo shell: {}", shellHome);
            return Paths.get(shellHome, "bin", "mongo").toString();
        }
    }
}
