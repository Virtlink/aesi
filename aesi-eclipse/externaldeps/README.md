# External Dependencies

This project contains the external dependencies of the Eclipse RCP project. The
RCP project, since it's an OSGi project, can only use dependencies that are OSGi
bundles. An OSGi bundle is simple a JAR with a manifest
(a `META-INF/MANIFEST.mf` file) that describes the bundle.

However, we want to be able to use any dependency from any Maven repository,
regardless of whether it is an OSGi bundle. To make this work, these normal
dependencies need to be added to this _externaldeps_ project, which then
takes the dependencies, wraps them together in a JAR, and slams a manifest
on top of it.

## Adding a Dependency

To add a non-bundle dependency to the Eclipse RCP project:

1.  Edit the `/externaldeps/pom.xml` file.

2.  Add the dependency like you would any Maven dependency. For example:

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.25</version>
        </dependency>

    > **Note**: You can define variables in the
    > `/releng/com.virtlink.aesi-eclipse.configuration/pom.xml` configuration
    > file. For example, a version number that must be the same across
    > dependencies.

3.  Under the configuration of the `maven-bundle-plugin`, in the
    `<Embed-Dependency>` element, add the dependency's _artifact ID_ to the
    comma-separated list. For example:

        <Embed-Dependency>
            <!-- ... -->,
            slf4j-api
        </Embed-Dependency>

4.  Under the same `maven-bundle-plugin`, in the `<Export-Package>` element,
    add the dependency's packages to the comma-separated list. Suffix each
    package with `;provider=metaborg;mandatory:=provider` to prevent
    conflicts, and optionally `;version=0` to fix the version to 0. For example:

        <Export-Package>
            org.slf4j.*;provider=metaborg;mandatory:=provider;version=0
        </Export-Package>

    > **Note**: Prefix the package with `!` to exclude it instead. Suffix the
    > package name with `.*` to also match any subpackages.

5.  Build and install the _externaldeps_ project so the Eclipse RCP project can
    find it.

        mvn clean install
