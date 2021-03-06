---
title: AESI
---

This is the documentation for the AESI adapters. AESI, pronounced _'easy'_,
is the Adaptable Editor Services Interface.

## IntelliJ
To work with AESI in IntelliJ:

1.  Import the `aesi-java` and `aesi-intellij` Gradle projects.
2.  Create a run configuration that runs the `runIde` task on the `aesi-intellij` project.

    > Set the VM options to:
    >
    >     -Djdk.util.zip.ensureTrailingSlash=false
    >
    > This [fixes](https://youtrack.jetbrains.com/issue/IDEA-177278#comment=27-2373201)
    > an issue with IntelliJ run from Gradle, where IntelliJ throws the
    > following exception:
    > 
    >     FileNotFoundException: Entry fileTemplates//Singleton.java.ft not found
3.  Build the project in the `aesi-intellij` folder using:

        ./gradlew check


## Contents

- [Building](building.md)

### AESI for Eclipse
- [IntelliJ Development](intellij-development.md)
- [Workspace Setup](eclipse-workspace-setup.md)
- [Dependencies](eclipse-dependencies.md)

### APIs
- [AESI Java](aesi-java/index.md)
- [AESI for IntelliJ](aesi-intellij/index.md)
- [AESI for Eclipse](aesi-eclipse/index.md)

### Miscellaneous
- [License](license.md)



