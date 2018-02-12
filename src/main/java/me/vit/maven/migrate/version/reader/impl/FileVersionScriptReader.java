package me.vit.maven.migrate.version.reader.impl;

import me.vit.maven.migrate.filter.ScriptFileFilter;
import me.vit.maven.migrate.options.SourceOptions;
import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.migrate.version.factory.VersionScriptFactory;
import me.vit.maven.migrate.version.reader.VersionScriptReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileVersionScriptReader implements VersionScriptReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileVersionScriptReader.class);
    private SourceOptions sourceOptions;
    private VersionScriptFactory factory;

    public FileVersionScriptReader(SourceOptions sourceOptions) {
        this.sourceOptions = sourceOptions;
        this.factory = new VersionScriptFactory(sourceOptions.getVersionAndNameSeparator());
    }

    @Override
    public List<VersionScript> readAll() {
        List<File> scripts = getScriptFiles();
        return buildVersionScripts(scripts);
    }

    private List<File> getScriptFiles() {
        LOGGER.debug("  └ getting script files from the specified dir: {}", sourceOptions.getScriptsDir().getPath());
        FileFilter filter = new ScriptFileFilter(sourceOptions.getVersionAndNameSeparator());
        File[] files = sourceOptions.getScriptsDir().listFiles(filter);
        return files != null ? Arrays.asList(files) : Collections.<File>emptyList();
    }

    private List<VersionScript> buildVersionScripts(List<File> scripts) {
        LOGGER.debug("  └ building version scripts..");
        List<VersionScript> versionScripts = new ArrayList<>(scripts.size());
        for (File script : scripts) {
            versionScripts.add(factory.versionFile(script));
        }
        return versionScripts;
    }
}
