package me.vit.maven.util;

public class StringUtil {

    public static String join(String ... strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
        }
        return builder.toString();
    }

    public static String join(int[] parts, String separator) {
        if (parts == null || parts.length == 0) {
            return "";
        }
        if (separator == null || separator.length() == 0) {
            separator = "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                builder.append(separator);
            }
            builder.append(parts[i]);
        }
        return builder.toString();
    }

    public static String join(int[] parts, String wrapperTemplate, String separator) {
        if (parts == null || parts.length == 0) {
            return "";
        }
        if (separator == null || separator.length() == 0) {
            separator = "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                builder.append(separator);
            }
            builder.append(String.format(wrapperTemplate, parts[i]));
        }
        return builder.toString();
    }

    public static boolean isBlank(String string) {
        return string == null || string.isEmpty() || string.trim().equals("");
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }
}
