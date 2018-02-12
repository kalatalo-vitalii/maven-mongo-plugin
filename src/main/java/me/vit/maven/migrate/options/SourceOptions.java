package me.vit.maven.migrate.options;

import me.vit.maven.exception.build.SourceOptionsBuildException;
import me.vit.maven.util.StringUtil;

import java.io.File;

public class SourceOptions {
    private File scriptsDir;
    private String versionAndNameSeparator;

    private SourceOptions(File scriptsDir, String versionAndNameSeparator) {
        this.scriptsDir = scriptsDir;
        this.versionAndNameSeparator = versionAndNameSeparator;
    }

    public File getScriptsDir() {
        return scriptsDir;
    }

    public String getVersionAndNameSeparator() {
        return versionAndNameSeparator;
    }

    public static class Builder {
        private File scriptsDir;
        private String versionAndNameSeparator;

        public Builder scriptsDir(File scriptsDir) {
            this.scriptsDir = scriptsDir;
            return this;
        }

        public Builder versionAndNameSeparator(String versionAndNameSeparator) {
            this.versionAndNameSeparator = versionAndNameSeparator;
            return this;
        }

        public SourceOptions build() {
            if (scriptsDir == null) {
                throw new SourceOptionsBuildException("'scripts directory' is not specified");
            }
            if (!scriptsDir.exists()) {
                throw new SourceOptionsBuildException("'scripts directory' doesn't exist");
            }
            if (!scriptsDir.isDirectory()) {
                throw new SourceOptionsBuildException("'scripts directory' is not a directory");
            }
            if (!scriptsDir.canRead()) {
                throw new SourceOptionsBuildException("'scripts directory' is not readable");
            }
            if (StringUtil.isBlank(versionAndNameSeparator)) {
                throw new SourceOptionsBuildException("'version number and name separator' is not specified");
            }
            return new SourceOptions(scriptsDir, versionAndNameSeparator);
        }
    }
}
