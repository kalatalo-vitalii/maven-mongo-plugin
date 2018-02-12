package me.vit.maven.exception.execute;

import me.vit.maven.exception.MongoPluginExecutionException;

public class ShellException extends MongoPluginExecutionException {

    public ShellException(String message) {
        super(message);
    }

    public ShellException(String message, Throwable cause) {
        super(message, cause);
    }
}
