package me.vit.maven.migrate.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class ScriptFileFilter implements FileFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptFileFilter.class);
    private Pattern nameMatchingPattern;

    public ScriptFileFilter(String versionNumberAndNameSeparator) {
        nameMatchingPattern = Pattern.compile(String.format("^[0-9.]*(%s)?.*.js", versionNumberAndNameSeparator));
    }

    @Override
    public boolean accept(File pathname) {
        String name = pathname.getName();
        LOGGER.debug("    └ checking acceptance of '{}' file", name);
        if (!pathname.isFile()) {
            LOGGER.debug("      └ not accepted: not a file", name);
            return false;
        }
        if (!nameMatchingPattern.matcher(pathname.getName()).matches()) {
            LOGGER.debug("      └ not accepted: not a .js file", name);
            return false;
        }
        LOGGER.debug("      └ accepted", name);
        return true;
    }

    Pattern getNameMatchingPattern() {
        return nameMatchingPattern;
    }
}
