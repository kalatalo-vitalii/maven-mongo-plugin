package me.vit.maven.migrate.version.provider;

import me.vit.maven.migrate.version.VersionScript;

import java.util.List;

public interface VersionScriptProvider {

    List<VersionScript> get();
}
