# Dependencies

## Adding a Bundle Dependency
Eclipse RCP projects only support OSGi bundles as dependencies. An OSGi bundle
is simply a JAR with a manifest (a `META-INF/MANIFEST.mf` file) that describes
the bundle. Some dependencies are already OSGi bundles. Here's how to add such
a dependency:

1.  In `/releng/eclipse/plugin/pom.xml`, add the dependency like you would
    any Maven dependency. For example:

        <dependency>
            <groupId>com.virtlink</groupId>
            <artifactId>com.virtlink.aesi-eclipse.externaldeps</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>

2.  In `/bundles/aesi-eclipse/META-INF/MANIFEST.mf`, add the dependency's
    _artifact ID_ to the `Require-Bundle` and `Eclipse-RegisterBoddy` lists.
    For example:
    
        Require-Bundle: org.eclipse.ui,
          org.eclipse.core.runtime,
          org.eclipse.jface.text,
          org.eclipse.ui.editors,
          com.virtlink.aesi-eclipse.externaldeps
        Eclipse-RegisterBuddy: 
          com.virtlink.aesi-eclipse.externaldeps



## Adding a Non-Bundle Dependency
However, not all dependencies are OSGi bundles. In that case it's easier to
add the dependency to the _external dependencies_ project, which then takes the
dependencies, wraps them together in a JAR, and slams a manifest on top of it.

1.  Edit the `/externaldeps/pom.xml` file.

2.  Add the dependency like you would any Maven dependency. For example:

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.25</version>
        </dependency>

    > **Note**: You can define variables in the `/releng/pom.xml` configuration
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


## Adding a Platform Dependency
An Eclipse platform dependency is a dependency that's part of the Eclipse platform.

1.  In `/bundles/aesi-eclipse/META-INF/MANIFEST.mf`, add the dependency's
    _artifact ID_ to the `Require-Bundle` list. For example, here we add
    `org.eclipse.debug.ui`:

        Require-Bundle: org.eclipse.ui,
          org.eclipse.core.runtime,
          org.eclipse.jface.text,
          org.eclipse.ui.editors,
          com.virtlink.aesi-eclipse.externaldeps,
          org.eclipse.debug.ui

2.  Build the project in Maven.

        ./build.sh

    This should download the dependencies.

3.  Go to the Maven repository and find the full path to the JAR. In this case:

        ~/.m2/repository/p2/osgi/bundle/org.eclipse.debug.ui/3.8.2.v20130130-171415/org.eclipse.debug.ui-3.8.2.v20130130-171415.jar

4.  Edit `/bundles/aesi-eclipse/com.virtlink.aesi-eclipse.iml` and add an entry for
    the new library. Use the path from step 3, and also use that to extract version
    information:

        <orderEntry type="module-library">
          <library name="Maven: p2.eclipse-plugin:org.eclipse.debug.ui:3.8.2.v20130130-171415">
            <CLASSES>
              <root url="jar://$MAVEN_REPOSITORY$/p2/osgi/bundle/org.eclipse.debug.ui/3.8.2.v20130130-171415/org.eclipse.debug.ui-3.8.2.v20130130-171415.jar!/" />
            </CLASSES>
            <JAVADOC />
            <SOURCES />
          </library>
        </orderEntry>

Now IntelliJ also recognizes the platform dependency.
