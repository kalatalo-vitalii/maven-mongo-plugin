package me.vit.maven.exception.build;

public class ShellOptionsBuildException extends EntityBuildException {
    private static final String ENTITY = "shell options";

    public ShellOptionsBuildException(String message) {
        super(ENTITY, message);
    }
}
