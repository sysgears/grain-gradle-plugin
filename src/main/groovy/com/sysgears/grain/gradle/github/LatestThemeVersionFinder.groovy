package com.sysgears.grain.gradle.github

import groovyx.net.http.HTTPBuilder
import org.gradle.api.Project

import static groovyx.net.http.Method.GET

class LatestThemeVersionFinder {

    static String lookup(final Project project, final String theme) {

        String latestVersion = null

        String normalizedTheme = GitHubHelper.normalizeThemeName(theme)

        def http = new HTTPBuilder("https://api.github.com/repos/$GitHubHelper.THEME_REPOS_HOLDER/$normalizedTheme/git/refs/tags")

        http.request(GET) { req ->
            headers.'User-Agent' = "Mozilla/5.0"

            response.success = { resp, reader ->
                List tagList = []
                reader.each {
                    if (it.ref) {
                        def matcher = it.ref =~ /refs\/tags\/v([0-9\.]+)/
                        if (matcher) {
                            tagList << matcher[0][1]
                        }
                    }
                }
                latestVersion = tagList?.size() > 0 ? tagList.last() : null
            }

            response.failure = { resp, reader ->
                project.logger.error("Couldn't fetch latest version of theme $theme. Got response:\n" +
                        "${resp.statusLine}${reader ? '\n' + reader : ''}")
            }
        }
    }
}
