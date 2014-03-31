package com.sysgears.grain.gradle.utils

class StringUtils {

    static String ensurePrefix(String value, String prefix) {
        value.startsWith(prefix) ? value : "$prefix$value"
    }
}
