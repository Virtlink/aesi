MAVEN=mvn
GRADLE=gradle
MARGS=
GARGS=--console rich
PREFIX=..

.PHONY: all clean docs \
		aesi clean.aesi docs.aesi \
		aesi-utils clean.aesi-utils docs.aesi-utils \
		aesi-eclipse clean.aesi-eclipse docs.aesi-eclipse \
		parent-poms clean.parent-poms
.SILENT:

all: aesi-eclipse
clean: clean.aesi-eclipse
docs: docs.aesi-eclipse

# aesi
aesi:
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/aesi/)

docs.aesi:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/aesi/)

clean.aesi:
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/aesi/)

# aesi-utils
aesi-utils:
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/aesi-utils/)

docs.aesi-utils:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/aesi-utils/)

clean.aesi-utils:
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/aesi-utils/)

# aesi-eclipse
aesi-eclipse: aesi aesi-utils parent-poms
	$(MAVEN) clean install $(MARGS) -f $(abspath $(PREFIX)/aesi-eclipse/externaldeps/pom.xml)
	$(MAVEN) clean install $(MARGS) -f $(abspath $(PREFIX)/aesi-eclipse/plugin/pom.xml)

docs.aesi-eclipse: docs.aesi docs.aesi-utils
	$(MAVEN) dokka:dokka $(MARGS) -f $(abspath $(PREFIX)/aesi-eclipse/plugin/pom.xml)

clean.aesi-eclipse: clean.aesi clean.aesi-utils clean.parent-poms
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/aesi-eclipse/plugin/pom.xml)
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/aesi-eclipse/externaldeps/pom.xml)

# parent-poms
parent-poms: aesi aesi-utils
	$(MAVEN) install $(MARGS) -f $(abspath $(PREFIX)/releng/pom.xml)
	$(MAVEN) install $(MARGS) -f $(abspath $(PREFIX)/releng/plugin/pom.xml)
	$(MAVEN) install $(MARGS) -f $(abspath $(PREFIX)/releng/externaldeps/pom.xml)

clean.parent-poms: clean.aesi
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/releng/externaldeps/pom.xml)
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/releng/plugin/pom.xml)
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/releng/pom.xml)