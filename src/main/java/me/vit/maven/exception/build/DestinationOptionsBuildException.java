package me.vit.maven.exception.build;

public class DestinationOptionsBuildException extends EntityBuildException {
    private static final String ENTITY = "destination options";

    public DestinationOptionsBuildException(String message) {
        super(ENTITY, message);
    }
}
