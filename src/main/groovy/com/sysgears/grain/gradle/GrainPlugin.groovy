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

        // Adds Grain tasks to the project.
        new GrainTaskHandler(project).with {
            grainGenerate description: 'Runs Grain generate command.', command: 'generate'
            grainPreview description: 'Runs Grain preview command.', command: 'preview'
            grainDeploy description: 'Runs Grain deploy command.', command: 'deploy'
            grainClean description: 'Runs Grain clean command.', command: 'clean'
        }

        project.afterEvaluate {

            configuration.projectDir = configuration.projectDir?: 'src/site'

            if (GrainEnvironment.exists(configuration.projectDir)) {

                // looks up the Grain version
                configuration.version = configuration.version ?:
                    GrainEnvironment.lookUpProperty(configuration.projectDir, 'grain.version')

                // sets up the dependency configuration for the plugin
                new DependencyHandler(project).with {
                    grain "com.sysgears.grain:grain:${configuration.version}"
                }
                new ConfigurationHandler(project, 'grain').with {
                    exclude group: 'rhino'
                    exclude group: 'commons-logging'
                }
            }
        }
    }
}