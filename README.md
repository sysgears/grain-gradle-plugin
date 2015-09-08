Grain Gradle Plugin
===================

Gradle plugin for [Grain][Grain] framework. Grain is a general purpose static site generator which allows
to build websites of any size and complexity, like project and documentation sites or custom blog resources.
Grain supports Markdown, RST and AsciiDoc markup rendering, page templates with embedded Groovy code,
resource compression and minification, code highlighting using Python Pygments and more.

The plugin allows to create and manage your static website using Gradle, thus making it much easier to
create sites for your existing Gradle projects.

Getting Started
===============

To use in Gradle 2.1 and later:

```groovy
plugins {
  id "com.sysgears.grain" version "0.2.3"
}

repositories {
    mavenCentral()
}
```

To use in earlier versions of Gradle:

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.sysgears.grain:grain-gradle-plugin:0.2.3"
  }
}

apply plugin: "com.sysgears.grain"

repositories {
    mavenCentral()
}
```

Then put a Grain site sources to the `src/site` directory. For this purpose you can use one of the
Grain [themes][Grain themes]. If you have already downloaded a Grain theme, simply copy its files to the `src/site`.
Alternatively, you may install a theme by running Gradle task of appropriate type. Just copy the following to your
*build.gradle* script:

```groovy
task grainInstall(type: com.sysgears.grain.gradle.GrainInstallTask) {
    theme = 'template' // theme to install, can be 'template', 'octopress', etc.
    version = '0.4.2'  // theme version
}
```

> If the `theme` argument isn't specified, [Theme Template][Theme Template] will be installed by default, and if you
don't set the `version`, the latest available version will be fetched.

As you have added the sources, you can execute the following Gradle tasks:

 - `gradle grainPreview` - launches your website in a preview mode
 - `gradle grainGenerate` - generates all the website files to the *destination* directory (see [filesystem layout][filesystem layout])
 - `gradle grainDeploy` - deploys the resulting files (see [deployment configuration][deployment configuration])
 - `gradle grainClean` - cleans the site *destination* and *target* directories

[filesystem layout]: http://sysgears.com/grain/docs/latest/#filesystem-layout
[deployment configuration]: http://sysgears.com/grain/docs/latest/#deployment-configuration

Configuration
-------------

Optionally, you can change the site sources directory by specifying the `grain.projectDir` property:

```groovy
grain {
    projectDir = '' // relative path to the site sources, default to 'src/site'
}
```

Also, you can set a specific Grain version, just be sure that it is compatible with your theme:

```groovy
grain {
    version = '0.6.5' // the Grain version, must be compatible with the theme version
}
```

To use the latest Grain snapshot, you should define the Sonatype snapshots repository first:

```groovy
repositories {
    mavenCentral()
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}

grain {
    version = '0.7.0-SNAPSHOT'
}
```

Advanced Usage
--------------

You can run your own Grain [commands][custom commands] from the build script. For example, if you
have the `compress` command to gzip site files before deploying, you can simply execute it by the
following `GrainTask`:

```groovy
task(grainCompress, type: com.sysgears.grain.gradle.GrainTask) {
   command = 'compress' // the name of the custom command
   // arguments = []    // the arguments to pass to the command
   // jvmArguments = [] // JVM arguments to launch Grain process
}
```
[custom commands]: http://sysgears.com/grain/docs/latest/#creating-your-own-commands

For further information please visit [Grain project website][Grain].

Contributing
============

Any person or company wanting to contribute to Grain Gradle plugin should follow
the following rules in order to their contribution being accepted.

Sign your Work
--------------

We require that all contributors "sign-off" on their commits.  This
certifies that the contribution is your original work, or you have rights to
submit it under the same license, or a compatible license.

Any contribution which contains commits that are not Signed-Off will not be
accepted.

To sign off on a commit you simply use the `--signoff` (or `-s`) option when
committing your changes:

    $ git commit -s -m "Adding a new widget driver for cogs."

This will append the following to your commit message:

    Signed-off-by: Your Name <your@email.com>

By doing this you certify the below:

    Developer's Certificate of Origin 1.1

If you wish to add the signoff to the commit message on your every commit
without the need to specify -s or --signoff, rename
.git/hooks/commit-msg.sample to .git/hooks/commit-msg and uncomment the lines:

``` sh
SOB=$(git var GIT_AUTHOR_IDENT | sed -n 's/^\(.*>\).*$/Signed-off-by: \1/p')
grep -qs "^$SOB" "$1" || echo "$SOB" >> "$1"
```

Developer's Certificate of Origin
---------------------------------

To help track the author of a patch as well as the submission chain,
and be clear that the developer has authority to submit a patch for
inclusion into this project please sign off your work.  The sign off
certifies the following:

    Developer's Certificate of Origin 1.1

    By making a contribution to the project, I certify that:

    (a) The contribution was created in whole or in part by me and I
        have the right to submit it under the open source license
        indicated in the file; or

    (b) The contribution is based upon previous work that, to the best
        of my knowledge, is covered under an appropriate open source
        license and I have the right under that license to submit that
        work with modifications, whether created in whole or in part
        by me, under the same open source license (unless I am
        permitted to submit under a different license), as indicated
        in the file; or

    (c) The contribution was provided directly to me by some other
        person who certified (a), (b) or (c) and I have not modified
        it.

    (d) I understand and agree that this project and the contribution
        are public and that a record of the contribution (including all
        personal information I submit with it, including my sign-off) is
        maintained indefinitely and may be redistributed consistent with
        this project or the open source license(s) involved.

    (e) I hereby grant to the project, SysGears, LLC and its successors;
        and recipients of software distributed by the Project a perpetual,
        worldwide, non-exclusive, no-charge, royalty-free, irrevocable
        copyright license to reproduce, modify, prepare derivative works of,
        publicly display, publicly perform, sublicense, and distribute this
        contribution and such modifications and derivative works consistent
        with this Project, the open source license indicated in the previous
        work or other appropriate open source license specified by the Project
        and approved by the Open Source Initiative(OSI)
        at http://www.opensource.org.

License
=======

Grain Gradle plugin is licensed under the terms of the [Apache License, Version 2.0][Apache License, Version 2.0].

[Grain]: http://sysgears.com/grain/
[Grain themes]: http://sysgears.com/grain/themes/
[Theme Template]: http://sysgears.com/grain/themes/template/
[Apache License, Version 2.0]: http://www.apache.org/licenses/LICENSE-2.0.html
[Developer Certificate of Origin]: https://raw.github.com/sysgears/grain/master/DCO
