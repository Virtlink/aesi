#!/bin/sh

cd aesi-java/
./gradlew dokka

cd ../aesi-intellij/
./gradlew dokka
