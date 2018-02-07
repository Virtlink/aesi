# PAPLJ for LSP
Language Server for the PAPLJ language.

## Development
Import `settings.gradle` in your IDE (e.g. IntelliJ) and open the `paplj-vscode`
project in VS Code.

Create a Gradle run configuration that invokes the `run` task of the `paplj-lsp` project.
Add program launch arguments, such as `-Dexec.args="-p 5007"` to start the language
server on a socket port.

First run the language server on a socket port, then run the client in VS Code.


