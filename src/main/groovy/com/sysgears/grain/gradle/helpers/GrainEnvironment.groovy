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
     * @param projectDir the Grain project directory
     * @param name the property name
     * @param value the value to return in case if the property is not found
     * @return the value of the project property if found, the default value otherwise
     */
    static String lookUpProperty(String projectDir, String name, String value = null) {
        def property = null
        def propertiesFile = getPropertiesFile(projectDir)

        if (propertiesFile.isFile()) {
            def props = new Properties()
            props.load(new FileInputStream(propertiesFile))
            property = props.getProperty(name)
        }

        property ?: value
    }

    /**
     * Checks if the Grain project is located in the given directory.
     *
     * @param projectDir the project directory
     * @return true if the Grain project is found, false otherwise
     */
    static boolean exists(String projectDir) {
        getPropertiesFile(projectDir).isFile()
    }

    /**
     * Returns the properties file object.
     *
     * @param projectDir the Grain project directory
     * @return the properties file object
     */
    private static File getPropertiesFile(String projectDir) {
        new File(projectDir ? "${projectDir}/${PROPERTIES_FILE}" : PROPERTIES_FILE)
    }
}
