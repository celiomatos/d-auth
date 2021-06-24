package br.com.dauth.config;

import java.util.Collection;

public class Utils {

    private Utils() {
    }

    public static boolean isNotNullAndNotEmpty(String value) {
        return value != null && value.length() > 0;
    }

    public static <T> boolean isCollectionNotNullAndNotEmpty(Collection<T> ts) {
        return null != ts && !ts.isEmpty();
    }
}
