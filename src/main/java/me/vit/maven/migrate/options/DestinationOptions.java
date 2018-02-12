package me.vit.maven.migrate.options;

import me.vit.maven.exception.build.DestinationOptionsBuildException;
import me.vit.maven.util.StringUtil;

public class DestinationOptions {
    private String collection;
    private String numberField;
    private String numberPartsField;
    private String nameField;

    public DestinationOptions(String collection, String numberField, String numberPartsField, String nameField) {
        this.collection = collection;
        this.numberField = numberField;
        this.numberPartsField = numberPartsField;
        this.nameField = nameField;
    }

    public String getCollection() {
        return collection;
    }

    public String getNumberField() {
        return numberField;
    }

    public String getNumberPartsField() {
        return numberPartsField;
    }

    public String getNameField() {
        return nameField;
    }

    public static class Builder {
        private String collection;
        private String numberField;
        private String numberPartsField;
        private String nameField;

        public Builder collection(String collection) {
            this.collection = collection;
            return this;
        }

        public Builder numberField(String numberField) {
            this.numberField = numberField;
            return this;
        }

        public Builder numberPartsField(String numberPartsField) {
            this.numberPartsField = numberPartsField;
            return this;
        }

        public Builder nameField(String nameField) {
            this.nameField = nameField;
            return this;
        }

        public DestinationOptions build() {
            if (StringUtil.isBlank(collection)) {
                throw new DestinationOptionsBuildException("'collection' is not specified");
            }
            if (StringUtil.isBlank(numberField)) {
                throw new DestinationOptionsBuildException("'numberField' is not specified");
            }
            if (StringUtil.isBlank(numberPartsField)) {
                throw new DestinationOptionsBuildException("'numberPartsField' is not specified");
            }
            return new DestinationOptions(collection, numberField, numberPartsField, nameField);
        }
    }
}
