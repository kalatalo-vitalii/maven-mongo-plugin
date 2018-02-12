package me.vit.maven.migrate.version.provider.impl;

import me.vit.maven.migrate.version.VersionDocument;
import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.migrate.version.reader.VersionDocumentReader;
import me.vit.maven.migrate.version.reader.VersionScriptReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class AllAfterLastAppliedVersionScriptProvider extends AbstractVersionScriptProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllAfterLastAppliedVersionScriptProvider.class);
    private VersionDocumentReader documentReader;

    public AllAfterLastAppliedVersionScriptProvider(VersionScriptReader scriptReader, VersionDocumentReader documentReader) {
        super(scriptReader);
        this.documentReader = documentReader;
    }

    @Override
    protected List<VersionScript> filter(List<VersionScript> availableVersionScripts) {
        VersionDocument lastAppliedVersion = documentReader.getLastVersion();
        if (lastAppliedVersion == null) {
            LOGGER.debug("  └ no last applied version found: processing all the found scripts");
            return availableVersionScripts;
        } else {
            LOGGER.debug("  └ last applied version: {}", lastAppliedVersion);
            Iterator<VersionScript> it = availableVersionScripts.listIterator();
            while (it.hasNext()) {
                VersionScript versionScript = it.next();
                if (versionScript.compareTo(lastAppliedVersion) <= 0) {
                    LOGGER.debug("  └ excluding script {} as applied yet", versionScript.getNumber());
                    it.remove();
                } else {
                    LOGGER.debug("  └ including script {} as not applied yet", versionScript.getNumber());
                }
            }
        }
        return availableVersionScripts;
    }
}
