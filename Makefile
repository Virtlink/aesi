MARGS=
GARGS=--console rich

.PHONY: all clean docs run-intellij aesi aesi-eclipse aesi-intellij paplj paplj-eclipse paplj-intellij clean.aesi clean.aesi-eclipse clean.aesi-intellij clean.paplj clean.paplj-eclipse clean.paplj-intellij docs.aesi docs.aesi-eclipse docs.aesi-intellij docs.paplj docs.paplj-eclipse docs.paplj-intellij
.SILENT:

all: paplj-eclipse paplj-intellij
clean: clean.paplj-eclipse clean.paplj-intellij
docs: docs.paplj-eclipse docs.paplj-intellij
run-intellij: paplj-intellij
	./gradlew runIde $(GARGS) -p paplj-intellij/

# aesi-eclipse
aesi-eclipse: aesi
	mvn install $(MARGS) -f releng/pom.xml
	mvn install $(MARGS) -f releng/aesi-eclipse/pom.xml
	mvn clean install $(MARGS) -f aesi-eclipse/externaldeps/pom.xml
	mvn clean install $(MARGS) -f aesi-eclipse/bundle/pom.xml

docs.aesi-eclipse: docs.aesi
	mvn dokka:dokka $(MARGS) -f aesi-eclipse/bundles/pom.xml

clean.aesi-eclipse: clean.aesi
	mvn clean $(MARGS) -f aesi-eclipse/bundle/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/externaldeps/pom.xml
	mvn clean $(MARGS) -f releng/aesi-eclipse/pom.xml
	mvn clean $(MARGS) -f releng/pom.xml

# paplj-intellij
paplj-intellij:
	./gradlew check $(GARGS) -p paplj-intellij/

docs.paplj-intellij:
	./gradlew dokka $(GARGS) -p paplj-intellij/

clean.paplj-intellij:
	./gradlew clean $(GARGS) -p paplj-intellij/

# paplj-lsp
paplj-lsp:
	./gradlew check $(GARGS) -p paplj-lsp/

docs.paplj-lsp:
	./gradlew dokka $(GARGS) -p paplj-lsp/

clean.paplj-lsp:
	./gradlew clean $(GARGS) -p paplj-lsp/

# paplj-eclipse
paplj-eclipse: paplj aesi-eclipse
	mvn install $(MARGS) -f releng/pom.xml
	mvn install $(MARGS) -f releng/paplj-eclipse/pom.xml
	mvn clean install $(MARGS) -f paplj-eclipse/externaldeps/pom.xml
	mvn clean install $(MARGS) -f paplj-eclipse/bundle/pom.xml

docs.paplj-eclipse: docs.paplj docs.aesi-eclipse
	mvn dokka:dokka $(MARGS) -f paplj-eclipse/bundles/pom.xml

clean.paplj-eclipse: clean.paplj clean.aesi-eclipse
	mvn clean $(MARGS) -f paplj-eclipse/bundle/pom.xml
	mvn clean $(MARGS) -f paplj-eclipse/externaldeps/pom.xml
	mvn clean $(MARGS) -f releng/paplj-eclipse/pom.xml
	mvn clean $(MARGS) -f releng/pom.xml
