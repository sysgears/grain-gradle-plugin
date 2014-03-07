package com.sysgears.grain.gradle.handlers

import com.sysgears.grain.gradle.GrainTask
import groovy.transform.TupleConstructor
import org.gradle.api.Project

/**
 * Creates Grain tasks and adds them to Gradle project.
 */
@TupleConstructor
class GrainTaskHandler {

    /** Gradle project to which the plugin is applied. */
    Project project

    def methodMissing(String name, args) {
        def settings = args[0]

        project.task(name, type: GrainTask) {
            group = settings.group ?: 'Grain'
            grainArgs = settings.args ? settings.args.split(' ') : []
            grainOpts = settings.opts ? settings.opts.split(' ') : []
            description = settings.description ?: ''
        }
    }
}
