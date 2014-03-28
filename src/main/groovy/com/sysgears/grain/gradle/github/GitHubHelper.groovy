package com.sysgears.grain.gradle.github

class GitHubHelper {

    static String THEME_REPOS_HOLDER = 'sysgears'

    static def normalizeThemeName = { String theme ->
        String normalizedThemePrefix = 'grain-theme-'
        theme.startsWith(normalizedThemePrefix) ? theme : "$normalizedThemePrefix$theme"
    }

    static def normalizeVersionName = { String version ->
        "v$version"
    }
}
