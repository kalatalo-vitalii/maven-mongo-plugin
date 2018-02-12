package me.vit.maven.migrate.version.reader;

import me.vit.maven.migrate.version.VersionScript;

import java.util.List;

public interface VersionScriptReader {

    List<VersionScript> readAll();
}
