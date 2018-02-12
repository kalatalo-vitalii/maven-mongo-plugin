package me.vit.maven.migrate.version.factory;

import me.vit.maven.migrate.version.Version;
import me.vit.maven.util.StringUtil;

public class JsonFactory {
    private static final String WRAPPER_TEMPLATE = "NumberInt(%d)";
    private FieldNames names;

    public JsonFactory(FieldNames names) {
        this.names = names;
    }

    public String versionJson(Version version) {
        return String.format("{%s:'%s',%s:[%s],%s:%s,timestamp:new Date()}",
                names.getNumber(), version.getNumber(),
                names.getNumberParts(), StringUtil.join(version.getNumberParts(), WRAPPER_TEMPLATE, ","),
                names.getName(), StringUtil.isBlank(version.getName()) ? "null" : String.format("'%s'", version.getName())
        );
    }

    public static class FieldNames {
        private String number;
        private String numberParts;
        private String name;

        public FieldNames(String number, String numberParts, String name) {
            this.number = number;
            this.numberParts = numberParts;
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public String getNumberParts() {
            return numberParts;
        }

        public String getName() {
            return name;
        }
    }
}
