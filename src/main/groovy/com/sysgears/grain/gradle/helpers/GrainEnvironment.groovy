package com.sysgears.grain.gradle.helpers

import org.gradle.api.Project

/**
 * Provides access to Grain environment.
 */
class GrainEnvironment {

    /**
     * Look ups the value of given Grain property.
     */
    static String lookUpProperty(Project project, String property, String value) {
        def specifiedGrainVersion = null

        def grainPropertiesFile = new File(project.grain.projectDir, 'application.properties')
        if (grainPropertiesFile.exists() && grainPropertiesFile.isFile()) {
            def grainProps = new Properties()
            grainProps.load(new FileInputStream(grainPropertiesFile))
            specifiedGrainVersion = grainProps.getProperty(property)
        }

        specifiedGrainVersion ?: value
    }
}
