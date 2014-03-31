package com.sysgears.grain.gradle.utils

/**
 *  Provides methods for operations with strings
 */
class StringUtils {

    /**
     * Appends specified prefix to a string, if the string doesn't already contains that prefix.
     *
     * @param value string to append prefix to
     * @param prefix prefix to append
     * @return string with prefix
     */
    static String ensurePrefix(String value, String prefix) {
        value.startsWith(prefix) ? value : "$prefix$value"
    }
}
