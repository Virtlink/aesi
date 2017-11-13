'use strict';
import * as net from 'net';
import * as path from 'path';

import { Trace } from 'vscode-jsonrpc';
import { workspace, ExtensionContext } from 'vscode';
import { LanguageClient, LanguageClientOptions, StreamInfo } from 'vscode-languageclient';

export function activate(context: ExtensionContext) {
    let serverOptions = getStdioServerOptions(context);
    
    let clientOptions: LanguageClientOptions = {
        documentSelector: ['paplj'],
        synchronize: {
            fileEvents: workspace.createFileSystemWatcher('**/*.pj')
        }
    };
    
    // Create the language client and start the client.
    let lc = new LanguageClient('PAPLJ Server', serverOptions, clientOptions);
    // enable tracing (.Off, .Messages, Verbose)
    lc.trace = Trace.Off;
    // lc.trace = Trace.Messages;
    // lc.trace = Trace.Verbose;
    let disposable = lc.start();
    
    // Push the disposable to the context's subscriptions so that the 
    // client can be deactivated on extension deactivation
    context.subscriptions.push(disposable);
}

function getSocketServerOptions(context: ExtensionContext) {
    // The server is a started as a separate app and listens on port 5007
    let connectionInfo = {
        port: 5007
    };
    let serverOptions = () => {
        // Connect to language server via socket
        let socket = net.connect(connectionInfo);
        let result: StreamInfo = {
            writer: socket,
            reader: socket
        };
        return Promise.resolve(result);
    };
    return serverOptions;
}

function getStdioServerOptions(context: ExtensionContext) {
    // The server is started as a separate Java process

    // Path where the language server is installed (contains a `bin` and `lib` directory)
    // NOTE: To install the language server, run `gradle installDist`.
    const executablePath = path.join('..', 'paplj-lsp', 'build', 'install', 'paplj-lsp')
    const executableName = 'paplj-lsp'
    const executableExt = process.platform == 'win32' ? '.bat' : '';
    const command = context.asAbsolutePath(path.join(executablePath, 'bin', executableName + executableExt));
    const serverOptions = {
        run: {
            command: command,
            args: []
        },
        debug: {
            command: command,
            args: [] //['--trace']
        }
    };
    return serverOptions;
}

// this method is called when your extension is deactivated
export function deactivate() {
}