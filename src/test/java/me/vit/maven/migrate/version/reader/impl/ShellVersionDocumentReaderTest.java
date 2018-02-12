package me.vit.maven.migrate.version.reader.impl;

import me.vit.maven.exception.execute.VersionDocumentReaderException;
import net.minidev.json.JSONArray;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ShellVersionDocumentReaderTest {

    @Test(expected = VersionDocumentReaderException.class)
    public void parseDateWhenBadString() {
        String dateString = "2017-03-24T14:21:43.581Z";

        Date date = ShellVersionDocumentReader.parseDate(dateString);

        assertNotNull(date);
    }

    @Test
    public void parseDate() {
        String dateString = "ISODate(\"2017-03-24T14:21:43.581Z\")";

        Date date = ShellVersionDocumentReader.parseDate(dateString);

        assertNotNull(date);
    }

    @Test(expected = Exception.class)
    public void getJsonFieldValueWhenNoField() throws Exception {
        String json = "{_id: 1, major: 1, minor: 1, name: \"name\", array: [1,2,3]}";

        assertNull(ShellVersionDocumentReader.getJsonFieldValue(json, "null"));
    }

    @Test
    public void getJsonFieldValue() throws Exception {
        String json = "{_id: 1, major: 1, minor: 1, name: \"name\", array: [1,2,3]}";

        assertEquals(1, ShellVersionDocumentReader.getJsonFieldValue(json, "major"));
        assertEquals(1, ShellVersionDocumentReader.getJsonFieldValue(json, "minor"));
        assertEquals("name", ShellVersionDocumentReader.getJsonFieldValue(json, "name"));

        JSONArray array = ShellVersionDocumentReader.getJsonFieldValue(json, "array");
        assertNotNull(array);
    }
}