package me.vit.maven.shell;

public class ShellQueryBuilder {

    public static String buildMaxArraySizeQuery(String collection, String arrayField, String resultField) {
        return String.format("db.getCollection('%s').aggregate([" +
                "{$unwind:'$%s'}," +
                "{$group:{_id:'$_id',%s:{$sum:1}}}," +
                "{$sort:{%s:-1}}," +
                "{$limit:1}])" +
                ".forEach(printjson)", collection, arrayField, resultField, resultField
        );
    }

    public static String buildMaxArrayValuesQuery(String collection, String arrayField, int maxArraySize) {
        return String.format("db.getCollection('%s').find({}).sort(%s).limit(1).forEach(printjson)",
                collection, buildSortOnArrayQuery(arrayField, maxArraySize)
        );
    }

    public static String buildSortOnArrayQuery(String arrayField, int maxArraySize) {
        if (maxArraySize == 0) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < maxArraySize; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(String.format("'%s.%d':-1", arrayField, i));
        }
        return builder.append("}").toString();
    }

    public static String buildFindAllQuery(String collection) {
        return String.format("db.getCollection('%s').find({}).forEach(printjson)", collection);
    }
}
