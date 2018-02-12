package me.vit.maven.exception;

public abstract class MongoPluginException extends RuntimeException {

    public MongoPluginException() {
    }

    public MongoPluginException(String message) {
        super(message);
    }

    public MongoPluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
