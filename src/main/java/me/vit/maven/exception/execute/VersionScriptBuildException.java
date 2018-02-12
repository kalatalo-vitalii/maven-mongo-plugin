package me.vit.maven.exception.execute;

import me.vit.maven.exception.MongoPluginExecutionException;

public class VersionScriptBuildException extends MongoPluginExecutionException {

    public VersionScriptBuildException(String message) {
        super(message);
    }
}
