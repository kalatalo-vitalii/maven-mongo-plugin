package me.vit.maven.exception.execute;

import me.vit.maven.exception.MongoPluginExecutionException;

public class VersionDocumentReaderException extends MongoPluginExecutionException {

    public VersionDocumentReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
