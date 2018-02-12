package me.vit.maven.exception;

public class MongoPluginExecutionException extends MongoPluginException {

    public MongoPluginExecutionException(String message) {
        super(message);
    }

    public MongoPluginExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
