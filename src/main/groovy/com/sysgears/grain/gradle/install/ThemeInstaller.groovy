package com.sysgears.grain.gradle.install

/**
 * Provides logic for Grain theme installation.
 */
class ThemeInstaller {

    /** Temp archive file to hold theme files. */
    private static final TEMP_THEME_FILE = 'theme.zip'

    /** Prefix for the temp theme installation directory. */
    private static final TEMP_DIR_PREFIX = 'grain-theme-installation'

    /**
     * Installs theme to a specified directory,
     *
     * @param downloadUrl url to fetch theme archive from
     * @param destDir theme destination dir
     * @param archiveFolder folder that contains theme files inside the downloaded archive
     */
    static void install(final String downloadUrl, final String destDir, final String archiveFolder) {
        def ant = new AntBuilder()

        String refinedDest = destDir.isEmpty() || destDir.endsWith('/') ? destDir : "$destDir/"

        // Creating temp directory
        ant.tempfile(property: 'tempFile', destDir: System.getProperty('java.io.tmpdir'), prefix: TEMP_DIR_PREFIX)
        def tempDir = ant.project.properties.tempFile
        ant.mkdir(dir: tempDir)

        // Checking if destination directory exists
        ant.available(property: 'destAvailable', file: refinedDest, type: 'dir')
        def destAvailable = ant.project.properties.destAvailable == 'true'

        try {
            ant.get(src: downloadUrl, verbose: 'true', dest: "${tempDir}/${TEMP_THEME_FILE}")

            ant.sequential {
                ant.unzip(src: "${tempDir}/${TEMP_THEME_FILE}", dest: "$tempDir", overwrite: 'true')
                if (!destAvailable) {
                    ant.mkdir(dir: refinedDest)
                }
                ant.copy(todir: "$refinedDest") {
                    fileset(dir: "${tempDir}/${archiveFolder}")
                }
            }
        } finally {
            ant.delete(dir: "${tempDir}")
        }
    }
}
