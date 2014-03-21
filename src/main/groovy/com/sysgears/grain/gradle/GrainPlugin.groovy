package com.sysgears.grain.gradle

import com.sysgears.grain.gradle.handlers.ConfigurationHandler
import com.sysgears.grain.gradle.handlers.DependencyHandler
import com.sysgears.grain.gradle.handlers.GrainTaskHandler
import com.sysgears.grain.gradle.helpers.GrainEnvironment
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Grain Gradle plugin implementation.
 */
class GrainPlugin implements Plugin<Project> {

    void apply(Project project) {

        def configuration = project.extensions.create('grain', GrainPluginExtension)

        configuration.onSetProjectDir {
            new GrainTaskHandler(project).with {
                grainGenerate description: 'Runs Grain generate command.', command: 'generate'
                grainPreview description: 'Runs Grain preview command.', command: 'preview'
                grainDeploy description: 'Runs Grain deploy command.', command: 'deploy'
                grainClean description: 'Runs Grain clean command.', command: 'clean'
            }

            def grainVersion = GrainEnvironment.lookUpProperty(project, 'grain.version', '')

            if (grainVersion) {
                new DependencyHandler(project).with {
                    grain "com.sysgears.grain:grain:$grainVersion"
                }

                new ConfigurationHandler(project, 'grain').with {
                    exclude group: 'rhino'
                    exclude group: 'commons-logging'
                }
            } else {
                project.logger.error("Error: Unable to find Grain project in the [$configuration.projectDir] directory.")
            }
        }
    }
}