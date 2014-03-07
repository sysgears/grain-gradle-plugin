package com.sysgears.grain.gradle.helpers

import org.gradle.api.Project

/**
 * Provides access to the project environment.
 */
class ProjectEnvironment {

    /**
     * Look ups the value of given project property.
     *
     * @param project the project to look up the property for
     * @param name the property name
     * @param value the value to return in case if the property is not found
     * @return the value of the project property if found, the default value otherwise
     */
    static String lookUpProjectProp(Project project, String name, String value) {
        project.hasProperty(name) && project."$name"?.trim() ?
            project."$name" : value
    }

    /**
     * Look ups the value of given environment variable.
     *
     * @param name the name of the environment variable
     * @param value the value to return in case if the variable is not found
     * @return the value of the environment variable if found, the default value otherwise
     */
    static String lookUpEnvVariable(String name, String value) {
        System.getenv(name)?.trim() ?: value
    }
}
