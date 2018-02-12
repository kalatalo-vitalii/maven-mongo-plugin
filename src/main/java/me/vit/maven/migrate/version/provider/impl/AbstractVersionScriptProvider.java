package me.vit.maven.migrate.version.provider.impl;

import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.migrate.version.VersionSorter;
import me.vit.maven.migrate.version.provider.VersionScriptProvider;
import me.vit.maven.migrate.version.reader.VersionScriptReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractVersionScriptProvider implements VersionScriptProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllAfterLastAppliedVersionScriptProvider.class);
    private VersionScriptReader scriptReader;
    protected List<VersionScript> versionScripts;

    public AbstractVersionScriptProvider(VersionScriptReader scriptReader) {
        this.scriptReader = scriptReader;
    }

    @Override
    public List<VersionScript> get() {
        if (versionScripts != null) {
            return versionScripts;
        }
        List<VersionScript> availableVersions = scriptReader.readAll();
        if (availableVersions.size() == 0) {
            LOGGER.warn("  └ no version scripts found");
        } else {
            LOGGER.debug("  └ ({}) version scripts found", availableVersions.size());
        }

        VersionSorter.sortAscending(availableVersions);
        versionScripts = filter(availableVersions);

        return versionScripts;
    }

    protected abstract List<VersionScript> filter(List<VersionScript> availableVersionScripts);
}
