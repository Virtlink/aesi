MAVEN=mvn
GRADLE=gradle
MARGS=
GARGS=--console rich
PREFIX=..

.PHONY: all clean docs \
		paplj clean.paplj docs.paplj \
		aesi-eclipse clean.aesi-eclipse docs.aesi-eclipse \
		paplj-eclipse clean.paplj-eclipse docs.paplj-eclipse
.SILENT:

all: paplj-eclipse
clean: clean.paplj-eclipse
docs: docs.paplj-eclipse

# paplj
paplj:
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/paplj/)

docs.paplj:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/paplj/)

clean.paplj:
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/paplj/)

# aesi-eclipse
aesi-eclipse:
	$(MAKE) all -C $(abspath $(PREFIX)/aesi-eclipse/)

docs.aesi-eclipse:
	$(MAKE) docs -C $(abspath $(PREFIX)/aesi-eclipse/)

clean.aesi-eclipse:
	$(MAKE) clean -C $(abspath $(PREFIX)/aesi-eclipse/)

# paplj-eclipse
paplj-eclipse: aesi-eclipse paplj
	$(MAVEN) clean install $(MARGS) -f $(abspath $(PREFIX)/paplj-eclipse/externaldeps/pom.xml)
	$(MAVEN) clean install $(MARGS) -f $(abspath $(PREFIX)/paplj-eclipse/plugin/pom.xml)

docs.paplj-eclipse: docs.aesi-eclipse docs.paplj
	$(MAVEN) dokka:dokka $(MARGS) -f $(abspath $(PREFIX)/paplj-eclipse/plugin/pom.xml)

clean.paplj-eclipse: clean.aesi-eclipse clean.paplj
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/paplj-eclipse/plugin/pom.xml)
	$(MAVEN) clean $(MARGS) -f $(abspath $(PREFIX)/paplj-eclipse/externaldeps/pom.xml)
