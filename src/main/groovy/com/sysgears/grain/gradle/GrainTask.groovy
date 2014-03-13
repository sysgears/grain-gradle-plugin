package com.sysgears.grain.gradle

import com.sysgears.grain.gradle.helpers.ProjectEnvironment
import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

/**
 * Grain task which provides default functionality for creating custom Grain actions.
 */
class GrainTask extends AbstractTask {

    /** Grain command. */
    String command = ''

    /** List of the command arguments. */
    List arguments = []

    /** List of JVM arguments to launch Grain process: system properties, heap size, etc. */
    List jvmArguments = []

    /**
     * Default Grain action.
     */
    @TaskAction
    void executeCommand() {
        project.javaexec {
            main = 'com.sysgears.grain.Main'
            classpath = project.sourceSets.grain.runtimeClasspath
            args = getGrainArguments()
            jvmArgs = getJvmArguments()
            standardInput = System.in
            workingDir = project.file("$project.grain.projectDir")
        }
    }

    /**
     * Gets Grain arguments, either provided by a task configuration or passed to the task as arguments.
     *
     * @return the arguments if found, empty list otherwise
     */
    List getGrainArguments() {
        List grainArgs = command ? [command] + arguments : []
        grainArgs ?: ProjectEnvironment.lookUpProjectProp(project, 'grainArgs', '')?.split(',')?.toList()?.findAll { it }
    }

    /**
     * Gets JVM arguments, either provided by a task configuration or passed to the task as arguments.
     *
     * @return the options if found, default options otherwise
     */
    List getJvmArguments() {
        jvmArguments ?: ProjectEnvironment.lookUpEnvVariable('GRAIN_OPTS', '-server -Xmx512M -Xms64M ' +
                '-XX:PermSize=32m -XX:MaxPermSize=256m -Dfile.encoding=UTF-8 ' +
                '-XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC').split(' ')?.toList()?.findAll { it }
    }
}
