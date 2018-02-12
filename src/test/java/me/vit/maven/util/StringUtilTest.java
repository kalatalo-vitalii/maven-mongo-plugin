package me.vit.maven.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilTest {
    @Test
    public void join() throws Exception {
        assertEquals("123", StringUtil.join(new int[]{1, 2, 3}, null));
        assertEquals("123", StringUtil.join(new int[]{1, 2, 3}, ""));
        assertEquals("1,2,3", StringUtil.join(new int[]{1, 2, 3}, ","));
    }
}