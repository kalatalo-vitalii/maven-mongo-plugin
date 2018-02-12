package me.vit.maven.migrate.version.factory;

import me.vit.maven.exception.execute.VersionScriptBuildException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VersionScriptFactoryTest {
    private static final String NUMBER_AND_NAME_DELIMITER = "__";
    private VersionScriptFactory factory;

    @Before
    public void before() {
        factory = new VersionScriptFactory(NUMBER_AND_NAME_DELIMITER);
    }

    @Test(expected = VersionScriptBuildException.class)
    public void getVersionNumberWhenNoNumber() {
        factory.getVersionNumber("__d");
    }

    @Test
    public void getVersionNumber() {
        assertEquals("1", factory.getVersionNumber("1__d"));
        assertEquals("1.1", factory.getVersionNumber("1.1__d"));
    }

    @Test
    public void getVersionName() throws Exception {
        assertEquals("", factory.getVersionName("1"));
        assertEquals("d", factory.getVersionName("1__d"));
        assertEquals("d5", factory.getVersionName("1.2.3__d5.js"));
        assertEquals("d 5", factory.getVersionName("1.2.3__d_5.js"));
    }
}