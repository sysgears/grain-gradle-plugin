package com.sysgears.grain.gradle.helpers

import org.gradle.api.Project

/**
 * Provides access to Grain environment.
 */
class GrainEnvironment {

    /** Grain properties file. */
    private final static PROPERTIES_FILE = 'application.properties'

    /**
     * Looks up the value of given Grain property.
     *
     * @param project the project to look up the property for
     * @param name the property name
     * @param value the value to return in case if the property is not found
     * @return the value of the project property if found, the default value otherwise
     */
    static String lookUpProperty(Project project, String name, String value) {
        def property = null
        def propertiesFile = project.file(project.grain.projectDir ?
            "${project.grain.projectDir}/${PROPERTIES_FILE}" : PROPERTIES_FILE)

        if (propertiesFile.exists() && propertiesFile.isFile()) {
            def props = new Properties()
            props.load(new FileInputStream(propertiesFile))
            property = props.getProperty(name)
        }

        property ?: value
    }
}
