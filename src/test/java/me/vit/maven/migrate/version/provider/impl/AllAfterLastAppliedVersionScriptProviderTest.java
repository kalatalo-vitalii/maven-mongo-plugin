package me.vit.maven.migrate.version.provider.impl;

import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.migrate.version.provider.VersionScriptProvider;
import me.vit.maven.migrate.version.reader.VersionDocumentReader;
import me.vit.maven.migrate.version.reader.VersionScriptReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class AllAfterLastAppliedVersionScriptProviderTest {
    @Mock
    private VersionScriptReader versionScriptReader;
    @Mock
    private VersionDocumentReader versionDocumentReader;
    private VersionScriptProvider provider;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        provider = new AllAfterLastAppliedVersionScriptProvider(versionScriptReader, versionDocumentReader);
    }

    @Test
    public void getWhenNoScripts() throws Exception {
        when(versionScriptReader.readAll()).thenReturn(VersionScriptProviderTestHelper.createVersionScripts(new int[]{}));
        when(versionDocumentReader.getLastVersion()).thenReturn(VersionScriptProviderTestHelper.createVersionDocument(2));

        List<VersionScript> result = provider.get();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void getWhenNoDocuments() throws Exception {
        when(versionScriptReader.readAll()).thenReturn(VersionScriptProviderTestHelper.createVersionScripts(new int[]{1, 2}));
        when(versionDocumentReader.getLastVersion()).thenReturn(null);

        List<VersionScript> result = provider.get();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getWhenSomeScriptsNotAppliedNoDocuments() throws Exception {
        when(versionScriptReader.readAll()).thenReturn(VersionScriptProviderTestHelper.createVersionScripts(new int[]{1, 2, 3}));
        when(versionDocumentReader.getLastVersion()).thenReturn(VersionScriptProviderTestHelper.createVersionDocument(2));

        List<VersionScript> result = provider.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getNumberParts()[0]);
    }
}