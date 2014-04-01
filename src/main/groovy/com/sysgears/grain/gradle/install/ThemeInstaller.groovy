package com.sysgears.grain.gradle.install

/**
 * Provides logic for Grain theme installation.
 */
class ThemeInstaller {

    /** Temp archive file to hold theme files. */
    private static final TEMP_THEME_FILE = 'theme.zip'

    /**
     * Installs theme to a specified directory,
     *
     * @param downloadUrl url to fetch theme archive from
     * @param destDir theme destination dir
     * @param archiveFolder folder that contains theme files inside the downloaded archive
     */
    static void install(final String downloadUrl, final String destDir, final String archiveFolder = null) {
        def ant = new AntBuilder()

        String refinedDest = destDir.isEmpty() || destDir.endsWith('/') ? destDir : "$destDir/"

        if (!new File(refinedDest).exists()) {
            ant.mkdir(dir: refinedDest)
        }

        ant.get(src: downloadUrl, verbose: 'true', dest: "${refinedDest}${TEMP_THEME_FILE}")

        ant.sequential {
            ant.unzip(src: "${refinedDest}${TEMP_THEME_FILE}", dest: "$refinedDest", overwrite: 'true')
            ant.delete(file: "${refinedDest}${TEMP_THEME_FILE}")
            if (archiveFolder) {
                ant.copy(todir: "$refinedDest") {
                    fileset(dir: "${refinedDest}${archiveFolder}")
                }
                ant.delete(dir: "${refinedDest}${archiveFolder}")
            }
        }
    }
}
