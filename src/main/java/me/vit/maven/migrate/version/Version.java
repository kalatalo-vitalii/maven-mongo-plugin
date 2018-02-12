package me.vit.maven.migrate.version;

import java.util.Arrays;

public class Version implements Comparable<Version> {
    protected String number;
    protected int[] numberParts;
    protected String name;

    public Version(String number, int[] numberParts, String name) {
        this.number = number;
        this.numberParts = numberParts;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public int[] getNumberParts() {
        return numberParts;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Version o) {
        int maxLength = Math.max(getNumberParts().length, o.getNumberParts().length);
        int[] thisParts = Arrays.copyOf(this.getNumberParts(), maxLength);
        int[] otherParts = Arrays.copyOf(o.getNumberParts(), maxLength);

        for (int i=0; i<maxLength; i++) {
            int compareResult = Integer.compare(thisParts[i], otherParts[i]);
            if (compareResult != 0) {
                return compareResult;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Version{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Version)) return false;

        Version version = (Version) o;

        return number.equals(version.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
