package me.vit.maven.exception.build;

import me.vit.maven.exception.MongoPluginInitializationException;

public class EntityBuildException extends MongoPluginInitializationException {

    public EntityBuildException(String entity, String message) {
        super(String.format("can't build '%s' entity: %s", entity, message));
    }
}
