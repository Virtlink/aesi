# Development in IntelliJ

To develop this project in IntelliJ, you need:

- [IntelliJ IDEA CE](https://www.jetbrains.com/idea/)
- [Osmorc plugin](https://plugins.jetbrains.com/plugin/1816-osmorc)

1.  Import the `aesi-eclipse` and `externaldeps` Maven projects.
2.  Import the `aesi-java` and `aesi-intellij` Gradle projects.
3.  Right-click the `aesi-eclipse` module and select _Add Framework Support..._.
4.  Check _OSGi_, and under _Libraries_ select _Download_. Click OK.
5.  Download all P2 dependencies so IntelliJ can find them, by executing this
    command in the `aesi-eclipse` folder:

        mvn org.apache.maven.plugins:maven-dependency-plugin:copy-dependencies \
            -DoutputDirectory='${pom.basedir}/p2-libs' \
            -DincludeGroupIds=p2.eclipse-plugin

6.  In IntelliJ, right-click the new `p2-libs` folder and choose _Add as
    Library..._.
7.  Use the name `p2-libs`, make it a _Project Library_, and add it to the
    module _aesi-eclipse_.

> **Note**: This still doesn't allow you to run Eclipse from IntelliJ, but at
> least provides you with all the IntelliJ IDE features you're used to. To run
> it, switch to Eclipse, refresh the project tree, and invoke the _Eclipse
> Application_ run configuration.

