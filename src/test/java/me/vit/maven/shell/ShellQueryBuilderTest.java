package me.vit.maven.shell;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShellQueryBuilderTest {
    @Test
    public void buildSortOnArrayQuery() throws Exception {
        assertEquals(
                "{}",
                ShellQueryBuilder.buildSortOnArrayQuery("array", 0)
        );
        assertEquals(
                "{'array.0':-1,'array.1':-1}",
                ShellQueryBuilder.buildSortOnArrayQuery("array", 2)
        );
    }
}