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
            def grainVersion = GrainEnvironment.lookUpProperty(project, 'grain.version', '')

            if (grainVersion) {
                new GrainTaskHandler(project).with {
                    grain description: 'Runs Grain command-line extension'
                    grainGenerate description: 'Runs Grain generate command', args: 'generate'
                    grainPreview description: 'Runs Grain preview command', args: 'preview'
                    grainDeploy description: 'Runs Grain deploy command', args: 'deploy'
                    grainClean description: 'Runs Grain clean command', args: 'clean'
                }

                new ConfigurationHandler(project).with {
                    exclude group: 'rhino'
                    exclude group: 'commons-logging'
                }

                project.sourceSets {
                    grain {
                        groovy {
                            srcDirs = [configuration.projectDir]
                        }
                    }
                }

                new DependencyHandler(project).with {
                    grainCompile "com.sysgears.grain:grain:$grainVersion"
                }
            }
        }
    }
}