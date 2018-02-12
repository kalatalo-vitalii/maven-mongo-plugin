package me.vit.maven.exception.build;

public class ShellBuildException extends EntityBuildException {
    private static final String ENTITY = "shell";

    public ShellBuildException(String message) {
        super(ENTITY, message);
    }
}
