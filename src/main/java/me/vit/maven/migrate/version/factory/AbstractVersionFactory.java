package me.vit.maven.migrate.version.factory;

import me.vit.maven.util.StringUtil;

public abstract class AbstractVersionFactory {

    static int[] getVersionNumberParts(String versionNumber) {
        if (StringUtil.isBlank(versionNumber)) {
            return null;
        }

        String[] parts = versionNumber.split("\\.");
        int[] result = new int[parts.length];
        for (int i = 0; i<parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        return result;
    }
}
