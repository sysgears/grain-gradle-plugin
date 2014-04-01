package com.sysgears.grain.gradle

import com.sysgears.grain.gradle.github.GitHubAPI
import com.sysgears.grain.gradle.install.ThemeInstaller
import com.sysgears.grain.gradle.utils.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskInstantiationException

/**
 * Grain task which provides functionality for Grain theme installation,
 */
class GrainInstallTask extends DefaultTask {

    /** GitHub username of the user or the organization. */
    private static final String GITHUB_USERNAME = 'sysgears'

    /** Prefix for the theme repositories. */
    private static final String THEME_REPO_PREFIX = 'grain-theme-'

    /** Prefix for the version tags. */
    private static final String VERSION_TAG_PREFIX = 'v'

    /** Grain theme to install. */
    String theme = 'template'

    /** Theme version to install. */
    String version

    /**
     * Default grain action.
     */
    @TaskAction
    def executeCommand() {

        // Validate installation dir
        File installationDir = new File(project.grain.projectDir)
        if (installationDir.exists() && installationDir.list().length > 0) {
            throw new InvalidUserDataException("Installation directory $project.grain.projectDir is not empty. Please " +
                    "specify an empty directory")
        }

        if (!theme) {
            throw new InvalidUserDataException('Please specify theme to download')
        }

        String gitHubRepo = StringUtils.ensurePrefix(theme, THEME_REPO_PREFIX)

        String gitHubTag

        if (version) {
            gitHubTag = StringUtils.ensurePrefix(version, VERSION_TAG_PREFIX)
        } else {
            // If there's no version specified, trying to look up version by getting release list from GitHub
            List tags = GitHubAPI.getTags(project, GITHUB_USERNAME, gitHubRepo)
            List releaseTags = tags.findAll { it =~ "${VERSION_TAG_PREFIX}[0-9\\.]+" }
            if (releaseTags?.size() > 0) {
                gitHubTag = releaseTags.last()
                version = gitHubTag.substring(VERSION_TAG_PREFIX.length())
            } else {
                throw new TaskInstantiationException("Couldn't fetch the latest version of the $theme theme. " +
                        "Try to specify theme version manually")
            }
        }

        ThemeInstaller.install(
                GitHubAPI.buildReleaseDownloadUrl(GITHUB_USERNAME, gitHubRepo, gitHubTag),
                project.grain.projectDir,
                "${gitHubRepo}-${version}"
        )
    }
}
