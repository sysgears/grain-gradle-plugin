package com.sysgears.grain.gradle.github

import org.gradle.api.Project

class ThemeInstaller {

    static void install(final Project project, final String theme, final String version) {
        def ant = new AntBuilder()

        def normalizedVersion = GitHubHelper.normalizeVersionName(version)
        def normalizedTheme = GitHubHelper.normalizeThemeName(theme)
        def projectDir = project.grain.projectDir.isEmpty() || project.grain.projectDir.endsWith('/') ?
            project.grain.projectDir : "$project.grain.projectDir/"

        ant.get(src: "https://github.com/${GitHubHelper.THEME_REPOS_HOLDER}/${normalizedTheme}/archive/${normalizedVersion}.zip",
                verbose: 'true', dest: "$projectDir")
        ant.sequential {
            ant.unzip(src: "${projectDir}${normalizedVersion}.zip", dest: "$projectDir", overwrite: 'true')
            ant.delete(file: "${projectDir}${normalizedVersion}.zip")
            ant.copy(todir: "$projectDir") {
                fileset(dir: "${projectDir}${normalizedTheme}-${version}")
            }
            ant.delete(dir: "${projectDir}${normalizedTheme}-${version}")
        }
    }
}
