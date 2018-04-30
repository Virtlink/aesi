NPM=npm
MAVEN=mvn
GRADLE=gradle
NARGS=
MARGS=
GARGS=
PREFIX=.

.PHONY: all clean docs \
		paplj clean.paplj docs.paplj \
		pie clean.pie docs.pie \
		pie-lsp clean.pie-lsp docs.pie-lsp \
		pie-intellij clean.pie-intellij docs.pie-intellij \
		pie-eclipse clean.pie-eclipse docs.pie-eclipse \
		pie-vscode clean.pie-vscode docs.pie-vscode \
		paplj-lsp clean.paplj-lsp docs.paplj-lsp \
		paplj-intellij clean.paplj-intellij docs.paplj-intellij \
		paplj-eclipse clean.paplj-eclipse docs.paplj-eclipse \
		paplj-vscode clean.paplj-vscode docs.paplj-vscode \
		metaborg-pie clean.metaborg-pie
.SILENT:

all: paplj pie
clean: clean.paplj clean.pie
docs: docs.paplj docs.pie

paplj: paplj-lsp paplj-intellij paplj-eclipse paplj-vscode
clean.paplj: clean.paplj-lsp clean.paplj-intellij clean.paplj-eclipse clean.paplj-vscode
docs.paplj: docs.paplj-lsp docs.paplj-intellij docs.paplj-eclipse docs.paplj-vscode

pie: pie-lsp pie-intellij pie-eclipse pie-vscode
clean.pie: clean.pie-lsp clean.pie-intellij clean.pie-eclipse clean.pie-vscode
docs.pie: docs.pie-lsp docs.pie-intellij docs.pie-eclipse docs.pie-vscode

# pie-lsp
pie-lsp: metaborg-pie
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/pie-lsp/)

docs.pie-lsp:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/pie-lsp/)

clean.pie-lsp: clean.metaborg-pie
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/pie-lsp/)

# pie-intellij
pie-intellij: metaborg-pie
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/pie-intellij/)

docs.pie-intellij:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/pie-intellij/)

clean.pie-intellij: clean.metaborg-pie
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/pie-intellij/)

# pie-eclipse
pie-eclipse: metaborg-pie
	#$(MAKE) all -C $(abspath $(PREFIX)/pie-eclipse/)

docs.pie-eclipse:
	#$(MAKE) docs -C $(abspath $(PREFIX)/pie-eclipse/)

clean.pie-eclipse: clean.metaborg-pie
	#$(MAKE) clean -C $(abspath $(PREFIX)/pie-eclipse/)

# pie-vscode
pie-vscode: metaborg-pie
	#cd $(PREFIX)/paplj-vscode/ && \
	#$(NPM) test

docs.pie-vscode:
	# Not supported

clean.pie-vscode: clean.metaborg-pie
	# Not supported

# paplj-lsp
paplj-lsp: metaborg-pie
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/paplj-lsp/)

docs.paplj-lsp:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/paplj-lsp/)

clean.paplj-lsp: clean.metaborg-pie
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/paplj-lsp/)

# paplj-intellij
paplj-intellij: metaborg-pie
	$(GRADLE) install $(GARGS) -p $(abspath $(PREFIX)/paplj-intellij/)

docs.paplj-intellij:
	$(GRADLE) dokka $(GARGS) -p $(abspath $(PREFIX)/paplj-intellij/)

clean.paplj-intellij: clean.metaborg-pie
	$(GRADLE) clean $(GARGS) -p $(abspath $(PREFIX)/paplj-intellij/)

# paplj-eclipse
paplj-eclipse: metaborg-pie
	$(MAKE) all -C $(abspath $(PREFIX)/paplj-eclipse/)

docs.paplj-eclipse:
	$(MAKE) docs -C $(abspath $(PREFIX)/paplj-eclipse/)

clean.paplj-eclipse: clean.metaborg-pie
	$(MAKE) clean -C $(abspath $(PREFIX)/paplj-eclipse/)

# paplj-vscode
paplj-vscode: metaborg-pie
	cd $(PREFIX)/paplj-vscode/ && \
	$(NPM) install
	# Disable tests, as they don't work
	# in a headless environment (e.g. Travis)
	# and we haven't defined any meaningful tests anyway.
	#$(NPM) test

docs.paplj-vscode:
	# Not supported

clean.paplj-vscode: clean.metaborg-pie
	# Not supported

metaborg-pie:
	$(MAVEN) install -f $(abspath $(PREFIX)/metaborg-util/log)
	$(MAVEN) install -f $(abspath $(PREFIX)/metaborg-util/vfs)
	$(MAVEN) install -f $(abspath $(PREFIX)/metaborg-util/util)
	$(MAVEN) install -f $(abspath $(PREFIX)/metaborg-pie/runtime/core)
	$(MAVEN) install -f $(abspath $(PREFIX)/metaborg-pie/runtime/builtin)

clean.metaborg-pie:
	$(MAVEN) clean -f $(abspath $(PREFIX)/metaborg-util/log)
	$(MAVEN) clean -f $(abspath $(PREFIX)/metaborg-util/vfs)
	$(MAVEN) clean -f $(abspath $(PREFIX)/metaborg-util/util)
	$(MAVEN) clean -f $(abspath $(PREFIX)/metaborg-pie/runtime/core)
	$(MAVEN) clean -f $(abspath $(PREFIX)/metaborg-pie/runtime/builtin)
