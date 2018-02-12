package me.vit.maven.exception.build;

public class SourceOptionsBuildException extends EntityBuildException {
    private static final String ENTITY = "source options";

    public SourceOptionsBuildException(String message) {
        super(ENTITY, message);
    }
}
