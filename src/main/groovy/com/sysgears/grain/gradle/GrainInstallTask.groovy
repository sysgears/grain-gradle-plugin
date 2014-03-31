package com.sysgears.grain.gradle

import com.sysgears.grain.gradle.github.GitHubAPI
import com.sysgears.grain.gradle.install.ThemeInstaller
import com.sysgears.grain.gradle.helpers.ProjectEnvironment
import com.sysgears.grain.gradle.helpers.StorageConstraints
import com.sysgears.grain.gradle.utils.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskInstantiationException

class GrainInstallTask extends DefaultTask {

    String theme

    String version

    @TaskAction
    def executeCommand() {

        // Validate installation dir
        File installationDir = new File(project.grain.projectDir)
        if (installationDir.exists() && installationDir.list().length > 0) {
            throw new InvalidUserDataException('Installation directory is not empty. Please specify an empty directory')
        }

        // Look up theme and theme version from task arguments
        if (!theme || !version) {
            def taskProps = ProjectEnvironment.lookUpProjectProp(project, 'grainArgs', '')?.
                    split(',')?.toList()?.findAll { it }
            if (taskProps && taskProps.size() > 0) {
                theme = theme ?: taskProps.head()
                version = version ?: taskProps.size() > 1 ? taskProps.get(1) : null
            }
        }

        // Look up theme and version in Grain configuration
        theme = theme ?: project.grain.theme
        version = version ?: project.grain.themeVersion

        if (!theme) {
            throw new InvalidUserDataException('Please specify theme to download')
        }

        String gitHubRepo = StringUtils.ensurePrefix(theme, StorageConstraints.THEME_REPO_PREFIX)

        String gitHubTag

        if (version) {
            gitHubTag = StringUtils.ensurePrefix(version, StorageConstraints.VERSION_TAG_PREFIX)
        } else {
            // If there's no version specified, trying to look up version by getting release list from GitHub
            List tags = GitHubAPI.getTags(project, StorageConstraints.GITHUB_USERNAME, gitHubRepo)
            List releaseTags = tags.findAll { it =~ "${StorageConstraints.VERSION_TAG_PREFIX}[0-9\\.]+" }
            if (releaseTags?.size() > 0) {
                gitHubTag = releaseTags.last()
                version = gitHubTag.substring(StorageConstraints.VERSION_TAG_PREFIX.length())
            } else {
                throw new TaskInstantiationException("Couldn't fetch the latest version of the $theme theme. " +
                        "Try to specify theme version manually")
            }
        }

        ThemeInstaller.install(
                GitHubAPI.buildReleaseDownloadUrl(StorageConstraints.GITHUB_USERNAME, gitHubRepo, gitHubTag),
                project.grain.projectDir,
                "${gitHubRepo}-${version}"
        )
    }
}
