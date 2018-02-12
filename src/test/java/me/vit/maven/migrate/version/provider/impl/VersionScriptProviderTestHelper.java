package me.vit.maven.migrate.version.provider.impl;

import me.vit.maven.migrate.version.VersionDocument;
import me.vit.maven.migrate.version.VersionScript;

import java.util.ArrayList;
import java.util.List;

public class VersionScriptProviderTestHelper {

    static List<VersionScript> createVersionScripts(int[] versions) {
        List<VersionScript> versionScripts = new ArrayList<>();
        for (int version : versions) {
            versionScripts.add(createVersionScript(version));
        }
        return versionScripts;
    }

    static List<VersionDocument> createVersionDocuments(int[] versions) {
        List<VersionDocument> versionDocuments = new ArrayList<>();
        for (int version : versions) {
            versionDocuments.add(createVersionDocument(version));
        }
        return versionDocuments;
    }

    static VersionScript createVersionScript(int version) {
        return new VersionScript(createVersionNumber(version), new int[] {version}, null, null);
    }

    static VersionDocument createVersionDocument(int version) {
        return new VersionDocument(createVersionNumber(version), new int[] {version}, null, null, null);
    }

    private static String createVersionNumber(int version) {
        return String.format("%03d", version);
    }
}
