package me.vit.maven.migrate.version.reader;

import me.vit.maven.migrate.version.VersionDocument;

import java.util.List;

public interface VersionDocumentReader {

    VersionDocument getLastVersion();

    List<VersionDocument> getAll();
}
