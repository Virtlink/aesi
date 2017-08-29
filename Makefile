MARGS=-X
GARGS=--console rich

.PHONY: all aesi-java aesi-eclipse aesi-intellij clean docs
.SILENT:

all: aesi-java aesi-intellij aesi-eclipse

aesi-java:
	aesi-java/gradlew     install $(GARGS) -p aesi-java/

aesi-intellij:
	aesi-intellij/gradlew check   $(GARGS) -p aesi-intellij/

aesi-eclipse:
	mvn install $(MARGS) -f aesi-eclipse/releng/pom.xml
	mvn install $(MARGS) -f aesi-eclipse/releng/eclipse/plugin/pom.xml
	mvn clean install $(MARGS) -f aesi-eclipse/externaldeps/pom.xml
	mvn clean verify  $(MARGS) -f aesi-eclipse/bundles/pom.xml

clean:
	mvn clean $(MARGS) -f aesi-eclipse/bundles/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/externaldeps/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/releng/eclipse/plugin/pom.xml
	mvn clean $(MARGS) -f aesi-eclipse/releng/pom.xml
	aesi-intellij/gradlew clean $(GARGS) -p aesi-intellij/
	aesi-java/gradlew     clean $(GARGS) -p aesi-java/

docs:
	aesi-java/gradlew     dokka $(GARGS) -p aesi-java/
	aesi-intellij/gradlew dokka $(GARGS) -p aesi-intellij/
	mvn dokka:dokka $(MARGS) -f aesi-eclipse/bundles/pom.xml

