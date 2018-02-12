package me.vit.maven.exception.execute;

import me.vit.maven.exception.MongoPluginExecutionException;

public class ProcessExecutorException extends MongoPluginExecutionException {

    public ProcessExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
