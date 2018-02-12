package me.vit.maven.migrate.version;

import java.io.File;

public class VersionScript extends Version {
    private File script;

    public VersionScript(String number, int[] numberParts, String name, File script) {
        super(number, numberParts, name);
        this.script = script;
    }

    public File getScript() {
        return script;
    }

    @Override
    public String toString() {
        return "VersionScript{" +
                "script=" + script.getName() +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
