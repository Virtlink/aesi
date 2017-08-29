#!/bin/sh

# AESI for Java
cd aesi-java/
./gradlew install --console rich
cd ../

# AESI for IntelliJ
cd aesi-intellij/
./gradlew check --console rich
cd ../

# AESI for Eclipse
cd aesi-eclipse/
cd releng/
mvn -q install
cd eclipse/plugin/
mvn -q install
cd ../../../externaldeps
mvn -q clean install
cd ../bundles
mvn -q clean verify
cd ../




