package me.vit.maven.migrate.version;

import me.vit.maven.migrate.version.factory.VersionScriptFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class VersionTest {
    private VersionScriptFactory versionScriptFactory;

    @Before
    public void before() {
        versionScriptFactory = new VersionScriptFactory("__");
    }

    @Test
    public void compareTo() throws Exception {
        Version v1;
        Version v2;

        v1 = versionScriptFactory.versionFile(new File("1__d"));
        v2 = versionScriptFactory.versionFile(new File("1.1__d"));
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v2.compareTo(v1) > 0);

        v1 = versionScriptFactory.versionFile(new File("1.1__d"));
        v2 = versionScriptFactory.versionFile(new File("1.1__d"));
        assertTrue(v1.compareTo(v2) == 0);
    }
}