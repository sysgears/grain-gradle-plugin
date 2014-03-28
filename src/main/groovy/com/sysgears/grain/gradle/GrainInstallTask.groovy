package com.sysgears.grain.gradle

import com.sysgears.grain.gradle.github.LatestThemeVersionFinder
import com.sysgears.grain.gradle.github.ThemeInstaller
import com.sysgears.grain.gradle.helpers.ProjectEnvironment
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskInstantiationException

class GrainInstallTask extends DefaultTask {

    String theme

    String version

    @TaskAction
    def executeCommand() {
        initializeArguments()

        if (!theme) {
            throw new InvalidUserDataException('Please specify theme to download')
        }

        if (!version) {
            throw new TaskInstantiationException("Couldn't fetch the latest version of the $theme theme. Try to specify theme version manually")
        }

        ThemeInstaller.install(project, theme, version)
    }

    private void initializeArguments() {
        if (!theme || !version) {
            def taskProps = ProjectEnvironment.lookUpProjectProp(project, 'grainArgs', '')?.
                    split(',')?.toList()?.findAll { it }
            if (taskProps && taskProps.size() > 0) {
                theme = theme ?: taskProps.head()
                version = version ?: taskProps.size() > 1 ? taskProps.get(1) :
                    LatestThemeVersionFinder.lookup(project, theme)
            }
        }
    }
}
