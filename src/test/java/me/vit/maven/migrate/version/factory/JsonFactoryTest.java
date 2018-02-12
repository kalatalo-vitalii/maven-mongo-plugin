package me.vit.maven.migrate.version.factory;

import me.vit.maven.migrate.version.Version;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonFactoryTest {
    private JsonFactory.FieldNames names;
    private JsonFactory factory;

    @Before
    public void before() {
        names = new JsonFactory.FieldNames("num", "p", "nam");
        factory = new JsonFactory(names);
    }

    @Test
    public void json() throws Exception {
        Version version = new Version("1.2.3", new int[]{1, 2, 3}, "a");

        String json = factory.versionJson(version);

        assertNotNull(json);
        assertEquals(version.getNumber(), JsonPath.read(json, String.format("$.%s", names.getNumber())));
        assertEquals(version.getName(), JsonPath.read(json, String.format("$.%s", names.getName())));
    }
}