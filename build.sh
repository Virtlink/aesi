#!/bin/sh

cd aesi-java/
./gradlew install
cd ../aesi-intellij/
./gradlew check

