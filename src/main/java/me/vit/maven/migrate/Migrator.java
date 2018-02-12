package me.vit.maven.migrate;

import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.migrate.version.apply.ScriptApplier;
import me.vit.maven.migrate.version.apply.VersionApplier;
import me.vit.maven.migrate.version.provider.VersionScriptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Migrator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Migrator.class);
    private VersionScriptProvider versionScriptProvider;
    private ScriptApplier scriptApplier;
    private VersionApplier versionApplier;

    public Migrator(VersionScriptProvider versionScriptProvider, ScriptApplier scriptApplier, VersionApplier versionApplier) {
        this.versionScriptProvider = versionScriptProvider;
        this.scriptApplier = scriptApplier;
        this.versionApplier = versionApplier;
    }

    public void migrate() {
        LOGGER.info("migration started..");
        List<VersionScript> versionScripts = versionScriptProvider.get();
        if (versionScripts.size() > 0) {
            for (VersionScript versionScript : versionScripts) {
                LOGGER.info("migrating to version: {}..", versionScript.getNumber());
                scriptApplier.apply(versionScript.getScript());
                versionApplier.apply(versionScript);
                LOGGER.info("..success");
            }
        } else {
            LOGGER.info("there are no scripts to apply");
        }
        LOGGER.info("migration finished..");
    }
}
