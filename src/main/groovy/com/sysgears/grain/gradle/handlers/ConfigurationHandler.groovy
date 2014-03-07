package com.sysgears.grain.gradle.handlers

import groovy.transform.TupleConstructor
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * Set ups Grain dependency configurations.
 */
@TupleConstructor
class ConfigurationHandler {

    /** List of Grain configurations */
    private final List configurations = ['grainCompile', 'grainRuntime']

    /** Gradle project to which the plugin is applied. */
    Project project

    def methodMissing(String name, args) {

        configurations.each { String confName ->
            Configuration conf = project.configurations.findByName(confName) ?: project.configurations.create(confName)
            conf."$name" args[0]
        }
    }
}
