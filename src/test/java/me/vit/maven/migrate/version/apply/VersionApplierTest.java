package me.vit.maven.migrate.version.apply;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VersionApplierTest {
    @Test
    public void isWriteSucceeded() throws Exception {
        String writeOutput = "WriteResult({ \"nInserted\" : 1 })\r\n";

        assertTrue(VersionApplier.isWriteSucceeded(writeOutput));
    }
}