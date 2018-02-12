package me.vit.maven.exception.execute;

import me.vit.maven.exception.MongoPluginExecutionException;

public class MigratorException extends MongoPluginExecutionException {

    public MigratorException(String message) {
        super(message);
    }
}
