package me.vit.maven.exception.execute;

import me.vit.maven.exception.MongoPluginExecutionException;

public class VersionApplierException extends MongoPluginExecutionException {

    public VersionApplierException(String message) {
        super(message);
    }
}
