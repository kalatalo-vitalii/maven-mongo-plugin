package me.vit.maven.migrate.version.factory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AbstractVersionFactoryTest {

    @Test
    public void getVersionNumberParts() {
        assertNull(AbstractVersionFactory.getVersionNumberParts(""));
        assertNotNull(AbstractVersionFactory.getVersionNumberParts("1"));

        int[] parts = AbstractVersionFactory.getVersionNumberParts("1.2.3");
        assertNotNull(parts);
        assertTrue(parts.length == 3);
        assertEquals(1, parts[0]);
        assertEquals(2, parts[1]);
        assertEquals(3, parts[2]);
    }
}