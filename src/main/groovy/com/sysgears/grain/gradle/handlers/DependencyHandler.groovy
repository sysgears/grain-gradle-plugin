package com.sysgears.grain.gradle.handlers

import groovy.transform.TupleConstructor
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * Specifies Grain Gradle plugin dependencies.
 */
@TupleConstructor
class DependencyHandler {

    /** Gradle project to which the plugin is applied. */
    Project project

    def methodMissing(String name, args) {
        Configuration configuration = project.configurations.findByName(name) ?: project.configurations.create(name)
        configuration.dependencies.add(project.dependencies.create(args[0]))
    }
}
