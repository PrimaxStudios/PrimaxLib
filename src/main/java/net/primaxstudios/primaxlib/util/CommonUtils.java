package net.primaxstudios.primaxlib.util;


import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public final class CommonUtils {

    private CommonUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static List<String> rawToList(String rawList, String splitter) {
        return Arrays.asList(rawList.split(splitter));
    }

    public static String listToRaw(List<String> list, String splitter) {
        StringJoiner joiner = new StringJoiner(splitter);
        for (String string : list) {
            joiner.add(string);
        }
        return joiner.toString();
    }

    public static String addToRaw(String rawList, String rawObject, String splitter) {
        return rawList.concat(splitter).concat(rawObject);
    }

    public static String removeFromRaw(String rawList, String rawObject, String splitter) {
        int index = rawList.indexOf(rawObject);
        if (index == -1) {
            return rawList;
        }
        int endOfObject = index + rawObject.length();
        if (rawList.contains(splitter)) {
            int splitterIndex;
            if (index == 0) {
                splitterIndex = endOfObject;
            } else {
                splitterIndex = index - splitter.length();
            }
            if (splitterIndex >= 0 && !rawList.startsWith(splitter, splitterIndex)) {
                return rawList;
            }
        }
        String beforeObject = rawList.substring(0, index);
        String afterObject = rawList.substring(endOfObject);
        if (!beforeObject.isEmpty() && rawList.startsWith(splitter, index - splitter.length())) {
            beforeObject = rawList.substring(0, index - splitter.length());
        } else if (!afterObject.isEmpty() && rawList.startsWith(splitter, endOfObject)) {
            afterObject = rawList.substring(endOfObject + splitter.length());
        }
        return beforeObject + afterObject;
    }
}
