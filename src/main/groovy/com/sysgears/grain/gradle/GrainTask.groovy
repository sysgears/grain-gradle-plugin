package com.sysgears.grain.gradle

import com.sysgears.grain.gradle.helpers.ProjectEnvironment
import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

/**
 * Grain task which provides default functionality for creating custom Grain actions.
 */
class GrainTask extends AbstractTask {

    /** List of Grain arguments: 'preview', 'generate', etc. */
    List grainArgs = []

    /** List of arguments to launch Grain process: system properties, heap size, etc. */
    List grainOpts = []

    /**
     * Default Grain action.
     */
    @TaskAction
    void executeCommand() {
        project.javaexec {
            main = 'com.sysgears.grain.Main'
            classpath = project.sourceSets.grain.runtimeClasspath
            args = getGrainArgs()
            jvmArgs = getGrainOpts()
            standardInput = System.in
            workingDir = project.file("$project.grain.base")
        }
    }

    /**
     * Gets Grain arguments, either provided by a task configuration or passed to the task as arguments.
     *
     * @return the arguments if found, empty list otherwise
     */
    List getGrainArgs() {
        grainArgs ?: ProjectEnvironment.lookUpProjectProp(project, 'grainArgs', '')?.split(',')?.toList()
    }

    /**
     * Gets Grain options, either provided by a task configuration or passed to the task as arguments.
     *
     * @return the options if found, default options otherwise
     */
    List getGrainOpts() {
        grainOpts ?: ProjectEnvironment.lookUpEnvVariable('GRAIN_OPTS', '-server -Xmx512M -Xms64M ' +
                '-XX:PermSize=32m -XX:MaxPermSize=256m -Dfile.encoding=UTF-8 ' +
                '-XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC').split(' ')?.toList()
    }
}
