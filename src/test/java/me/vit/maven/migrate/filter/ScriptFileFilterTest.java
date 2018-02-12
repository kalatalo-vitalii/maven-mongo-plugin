package me.vit.maven.migrate.filter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ScriptFileFilterTest {
    private ScriptFileFilter filter;

    @Before
    public void before() {
        filter = new ScriptFileFilter("__");
    }

    @Test
    public void testNameMatchingPattern() {
        assertTrue(filter.getNameMatchingPattern().matcher("001.js").matches());
        assertTrue(filter.getNameMatchingPattern().matcher("001__name.js").matches());
        assertTrue(filter.getNameMatchingPattern().matcher("001__name_name.js").matches());
    }
}