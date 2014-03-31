package com.sysgears.grain.gradle.github

import groovyx.net.http.HTTPBuilder
import org.gradle.api.Project
import static groovyx.net.http.Method.GET

/**
 * Provides interaction with GitHub service
 */
class GitHubAPI {

    /**
     * Returns tag list
     *
     * @param project project instance
     * @param username GitHub username of user or organization
     * @param repo repository name
     * @return list of tags
     */
    static List getTags(final Project project, final username, final String repo) {

        List tagList = []

        def http = new HTTPBuilder("https://api.github.com/repos/$username/$repo/git/refs/tags")

        http.request(GET) { req ->
            headers.'User-Agent' = "Mozilla/5.0"

            response.success = { resp, reader ->
                reader.each {
                    if (it.ref) {
                        def matcher = it.ref =~  /refs\/tags\/(.+)/
                        if (matcher) {
                            tagList << matcher[0][1]
                        }
                    }
                }
            }

            response.failure = { resp, reader ->
                project.logger.error("Couldn't get tag list for $repo repo. Got response:\n" +
                        "${resp.statusLine}${reader ? '\n' + reader : ''}")
            }
        }

        tagList
    }

    /**
     * Builds url to a release.
     *
     * @param username GitHub username of user or organization
     * @param repo repository name
     * @param releaseTag tag of the requested release
     * @return download url
     */
    static String buildReleaseDownloadUrl(String username, String repo, String releaseTag) {
        "https://github.com/${username}/${repo}/archive/${releaseTag}.zip"
    }
}
