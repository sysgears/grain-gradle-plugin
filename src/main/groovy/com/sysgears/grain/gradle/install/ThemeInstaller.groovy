package com.sysgears.grain.gradle.install

/**
 * Provides logic for Grain theme installation.
 */
class ThemeInstaller {

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

        String tempFile = 'theme.zip'

        if (!new File(refinedDest).exists()) {
            ant.mkdir(dir: refinedDest)
        }

        ant.get(src: downloadUrl, verbose: 'true', dest: "${refinedDest}${tempFile}")

        ant.sequential {
            ant.unzip(src: "${refinedDest}${tempFile}", dest: "$refinedDest", overwrite: 'true')
            ant.delete(file: "${refinedDest}${tempFile}")
            if (archiveFolder) {
                ant.copy(todir: "$refinedDest") {
                    fileset(dir: "${refinedDest}${archiveFolder}")
                }
                ant.delete(dir: "${refinedDest}${archiveFolder}")
            }
        }
    }
}
