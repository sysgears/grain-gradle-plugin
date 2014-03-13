package com.sysgears.grain.gradle.helpers

import org.gradle.api.Project

/**
 * Provides access to Grain environment.
 */
class GrainEnvironment {

    /** Grain properties file. */
    private final static PROPERTIES_FILE = 'application.properties'

    /**
     * Look ups the value of given Grain property.
     */
    static String lookUpProperty(Project project, String property, String value) {
        def specifiedGrainVersion = null
        def grainPropertiesFile = project.grain.projectDir ?
            new File(project.grain.projectDir as String, PROPERTIES_FILE) : new File(PROPERTIES_FILE)
        if (grainPropertiesFile.exists() && grainPropertiesFile.isFile()) {
            def grainProps = new Properties()
            grainProps.load(new FileInputStream(grainPropertiesFile))
            specifiedGrainVersion = grainProps.getProperty(property)
        }

        specifiedGrainVersion ?: value
    }
}
