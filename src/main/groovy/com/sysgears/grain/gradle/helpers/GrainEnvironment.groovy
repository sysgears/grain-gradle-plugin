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
        def specifiedGrainVersion = null
        def grainPropertiesFile = project.grain.projectDir ?
            new File(project.grain.projectDir as String, PROPERTIES_FILE) : new File(PROPERTIES_FILE)
        if (grainPropertiesFile.exists() && grainPropertiesFile.isFile()) {
            def grainProps = new Properties()
            grainProps.load(new FileInputStream(grainPropertiesFile))
            specifiedGrainVersion = grainProps.getProperty(name)
        }

        specifiedGrainVersion ?: value
    }
}
