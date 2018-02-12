package me.vit.maven.migrate.version;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VersionSorter {
    private static final Comparator<Version> ASCENDING_COMPARATOR = new Comparator<Version>() {

        @Override
        public int compare(Version o1, Version o2) {
            return o1.compareTo(o2);
        }
    };
    private static final Comparator<Version> DESCENDING_COMPARATOR = new Comparator<Version>() {

        @Override
        public int compare(Version o1, Version o2) {
            return o2.compareTo(o1);
        }
    };

    public static void sortAscending(List<? extends Version> versions) {
        Collections.sort(versions, ASCENDING_COMPARATOR);
    }

    public static void sortDescending(List<? extends Version> versions) {
        Collections.sort(versions, DESCENDING_COMPARATOR);
    }
}
