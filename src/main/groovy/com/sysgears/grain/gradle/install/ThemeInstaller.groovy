package com.sysgears.grain.gradle.install

class ThemeInstaller {

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
