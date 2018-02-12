package me.vit.maven.migrate.version;

import java.util.Date;

public class VersionDocument extends Version {
    private String id;
    private Date timestamp;

    public VersionDocument(String number, int[] numberParts, String name, String id, Date timestamp) {
        super(number, numberParts, name);
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
