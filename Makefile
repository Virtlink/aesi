MARGS=
GARGS=--console rich

.PHONY: all clean docs aesi aesi-eclipse aesi-intellij paplj paplj-eclipse paplj-intellij clean.aesi clean.aesi-eclipse clean.aesi-intellij clean.paplj clean.paplj-eclipse clean.paplj-intellij docs.aesi docs.aesi-eclipse docs.aesi-intellij docs.paplj docs.paplj-eclipse docs.paplj-intellij
.SILENT:

all: paplj-eclipse paplj-intellij
clean: clean.paplj-eclipse clean.paplj-intellij
docs: docs.paplj-eclipse docs.paplj-intellij

# aesi
aesi:
	aesi/gradlew install $(GARGS) -p aesi/

docs.aesi:
	aesi/gradlew dokka $(GARGS) -p aesi/

clean.aesi:
	aesi/gradlew clean $(GARGS) -p aesi/

# aesi-intellij
aesi-intellij: aesi
	aesi-intellij/gradlew check $(GARGS) -p aesi-intellij/

docs.aesi-intellij: docs.aesi
	aesi-intellij/gradlew  dokka $(GARGS) -p aesi-intellij/

clean.aesi-intellij: clean.aesi
	aesi-intellij/gradlew  clean $(GARGS) -p aesi-intellij/

# aesi-eclipse
aesi-eclipse: aesi
	mvn install $(MARGS) -f aesi-eclipse/releng/pom.xml
	mvn install $(MARGS) -f aesi-eclipse/releng/eclipse-plugin/pom.xml
	mvn clean install $(MARGS) -f aesi-eclipse/externaldeps/pom.xml
	mvn clean verify  $(MARGS) -f aesi-eclipse/bundle/pom.xml

docs.aesi-eclipse: docs.aesi
	mvn dokka:dokka $(MARGS) -f aesi-eclipse/bundles/pom.xml

clean.aesi-eclipse: clean.aesi
	mvn clean $(MARGS) -f aesi-eclipse/bundle/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/externaldeps/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/releng/eclipse-plugin/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/releng/pom.xml

# paplj
paplj: aesi
	paplj/gradlew install $(GARGS) -p paplj/

docs.paplj: docs.aesi
	paplj/gradlew dokka $(GARGS) -p paplj/

clean.paplj: clean.aesi
	paplj/gradlew clean $(GARGS) -p paplj/

# paplj-intellij
paplj-intellij: paplj aesi-intellij
	paplj-intellij/gradlew check $(GARGS) -p paplj-intellij/

docs.paplj-intellij: docs.paplj docs.aesi-intellij
	paplj-intellij/gradlew dokka $(GARGS) -p paplj-intellij/

clean.paplj-intellij: clean.paplj clean.aesi-intellij
	paplj-intellij/gradlew clean $(GARGS) -p paplj-intellij/

# paplj-eclipse
paplj-eclipse: paplj aesi-eclipse
	# TODO

docs.paplj-eclipse: docs.paplj docs.aesi-eclipse
	# TODO

clean.paplj-eclipse: clean.paplj clean.aesi-eclipse
	# TODO

