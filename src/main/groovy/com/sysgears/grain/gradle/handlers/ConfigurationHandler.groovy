package com.sysgears.grain.gradle.handlers

import groovy.transform.TupleConstructor
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * Sets up dependency configuration for the plugin.
 */
@TupleConstructor
class ConfigurationHandler {

    /** Gradle project to which the plugin is applied. */
    Project project

    /** Grain configuration. */
    String configuration

    def methodMissing(String name, args) {
            Configuration conf = project.configurations.findByName(configuration) ?:
                project.configurations.create(configuration)
            conf."$name" args[0]
    }
}
