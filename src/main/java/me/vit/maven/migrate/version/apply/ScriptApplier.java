package me.vit.maven.migrate.version.apply;

import me.vit.maven.shell.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ScriptApplier {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptApplier.class);
    private Shell shell;

    public ScriptApplier(Shell shell) {
        this.shell = shell;
    }

    public void apply(File script) {
        LOGGER.debug("  └ applying script: {}", script.getName());
        shell.executeJs(script);
    }
}
