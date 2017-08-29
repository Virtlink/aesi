---
title: alltypes - aesi-eclipse
---

### All Types

| [com.virtlink.aesi.eclipse.debugging.model.AbstractDebugThread](../com.virtlink.aesi.eclipse.debugging.model/-abstract-debug-thread/index.html) | Abstract base class for debug thread implementations. |
| [com.virtlink.aesi.debugging.AbstractDebugger](../com.virtlink.aesi.debugging/-abstract-debugger/index.html) | Abstract base class for debuggers. The debugger must call the ``[`#fireThreadCreated`](#) and ``[`#fireThreadDestroyed`](#) methods for any threads that are created or destroyed after the debugger has been attached and before the debugger has been detached. Then, for each thread created, the debugger must call either the ``[`#fireThreadResumed`](#) or the ``[`#fireThreadSuspended`](#) methods to indicate the running state of the thread. |
| [com.virtlink.aesi.debugging.AbstractSyncDebugger](../com.virtlink.aesi.debugging/-abstract-sync-debugger/index.html) | Executes synchronous actions asynchronously. |
| [com.virtlink.aesi.eclipse.debugging.model.AesiBreakpoint](../com.virtlink.aesi.eclipse.debugging.model/-aesi-breakpoint/index.html) |  |
| [com.virtlink.aesi.eclipse.debugging.model.AesiBreakpointAdapterFactory](../com.virtlink.aesi.eclipse.debugging.model/-aesi-breakpoint-adapter-factory/index.html) |  |
| [com.virtlink.aesi.eclipse.debugging.model.AesiBreakpointManager](../com.virtlink.aesi.eclipse.debugging.model/-aesi-breakpoint-manager/index.html) | Manages breakpoints. |
| [com.virtlink.aesi.eclipse.codecompletion.AesiCompletionProcessor](../com.virtlink.aesi.eclipse.codecompletion/-aesi-completion-processor/index.html) | A code completion processor for AESI languages. |
| [com.virtlink.aesi.eclipse.structureoutline.AesiContentProvider](../com.virtlink.aesi.eclipse.structureoutline/-aesi-content-provider/index.html) |  |
| [com.virtlink.aesi.eclipse.debugging.model.AesiDebugElement](../com.virtlink.aesi.eclipse.debugging.model/-aesi-debug-element/index.html) | A debug element. |
| [com.virtlink.aesi.eclipse.editors.AesiDocument](../com.virtlink.aesi.eclipse.editors/-aesi-document/index.html) | An AESI document. |
| [com.virtlink.aesi.eclipse.editors.AesiDocumentManager](../com.virtlink.aesi.eclipse.editors/-aesi-document-manager/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.AesiDocumentProvider](../com.virtlink.aesi.eclipse.editors/-aesi-document-provider/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.AesiFileDocument](../com.virtlink.aesi.eclipse.editors/-aesi-file-document/index.html) | A file-backed document. |
| [com.virtlink.aesi.eclipse.structureoutline.AesiLabelProvider](../com.virtlink.aesi.eclipse.structureoutline/-aesi-label-provider/index.html) |  |
| [com.virtlink.aesi.eclipse.launching.AesiLaunchConfigurationDelegate](../com.virtlink.aesi.eclipse.launching/-aesi-launch-configuration-delegate/index.html) | The launch configuration delegate. |
| [com.virtlink.aesi.eclipse.launching.AesiLaunchConfigurationTabGroup](../com.virtlink.aesi.eclipse.launching/-aesi-launch-configuration-tab-group/index.html) |  |
| [com.virtlink.aesi.eclipse.debugging.model.AesiLineBreakpointAdapter](../com.virtlink.aesi.eclipse.debugging.model/-aesi-line-breakpoint-adapter/index.html) |  |
| [com.virtlink.aesi.eclipse.structureoutline.AesiOutlinePage](../com.virtlink.aesi.eclipse.structureoutline/-aesi-outline-page/index.html) |  |
| [com.virtlink.aesi.eclipse.AesiPlugin](../com.virtlink.aesi.eclipse/-aesi-plugin/index.html) | The activator class controls the plug-in life cycle |
| [com.virtlink.aesi.eclipse.editors.AesiSourceViewerConfiguration](../com.virtlink.aesi.eclipse.editors/-aesi-source-viewer-configuration/index.html) | The AESI source viewer configuration. |
| [com.virtlink.aesi.eclipse.debugging.model.AesiStackFrame](../com.virtlink.aesi.eclipse.debugging.model/-aesi-stack-frame/index.html) | A stack frame. |
| [com.virtlink.aesi.eclipse.structureoutline.AesiStructureSymbol](../com.virtlink.aesi.eclipse.structureoutline/-aesi-structure-symbol/index.html) |  |
| [com.virtlink.aesi.eclipse.AesiUtils](../com.virtlink.aesi.eclipse/-aesi-utils/index.html) |  |
| [com.virtlink.aesi.eclipse.syntaxcoloring.AsyncPresentationReconciler](../com.virtlink.aesi.eclipse.syntaxcoloring/-async-presentation-reconciler/index.html) | Implementation of the `IPresentationReconciler` that assumes that its presentation repairer may take some time. The damagers however should be quick, since they'll be executed on the main thread. |
| [com.virtlink.aesi.codecompletion.CompletionProposal](../com.virtlink.aesi.codecompletion/-completion-proposal/index.html) | A completion proposal. |
| [org.slf4j.impl.ConsoleLoggerAdapter](../org.slf4j.impl/-console-logger-adapter/index.html) | Logs to the console view. |
| [com.virtlink.aesi.eclipse.Contract](../com.virtlink.aesi.eclipse/-contract/index.html) | Pre- and post-conditions. |
| [com.virtlink.aesi.codecompletion.DummyCodeCompleter](../com.virtlink.aesi.codecompletion/-dummy-code-completer/index.html) |  |
| [com.virtlink.aesi.structureoutline.DummyStructureOutliner](../com.virtlink.aesi.structureoutline/-dummy-structure-outliner/index.html) |  |
| [com.virtlink.aesi.syntaxhighlighting.DummySyntaxColorizer](../com.virtlink.aesi.syntaxhighlighting/-dummy-syntax-colorizer/index.html) | A dummy implementation of the syntax colorizer interface. |
| [org.slf4j.impl.EclipseLoggerAdapter](../org.slf4j.impl/-eclipse-logger-adapter/index.html) | Logs to the Eclipse Error view. |
| [org.slf4j.impl.EclipseLoggerFactory](../org.slf4j.impl/-eclipse-logger-factory/index.html) | Creates Eclipse logger adapters. |
| [com.virtlink.aesi.debugging.IAttachOptions](../com.virtlink.aesi.debugging/-i-attach-options/index.html) | Options for attaching to a program. |
| [com.virtlink.aesi.codecompletion.ICodeCompleter](../com.virtlink.aesi.codecompletion/-i-code-completer/index.html) | A code completer. |
| [com.virtlink.aesi.debugging.IDebugSession](../com.virtlink.aesi.debugging/-i-debug-session/index.html) | A debug session. |
| [com.virtlink.aesi.debugging.IDebuggerCapabilities](../com.virtlink.aesi.debugging/-i-debugger-capabilities/index.html) | Specifies the capabilities of the debugger. |
| [com.virtlink.aesi.debugging.IEvaluator](../com.virtlink.aesi.debugging/-i-evaluator/index.html) | Evaluates expressions. |
| [com.virtlink.aesi.debugging.IProcess](../com.virtlink.aesi.debugging/-i-process.html) | Created by daniel on 6/29/17. |
| [com.virtlink.aesi.debugging.IValue](../com.virtlink.aesi.debugging/-i-value.html) | A value. |
| [com.virtlink.aesi.eclipse.editors.IXMLColorConstants](../com.virtlink.aesi.eclipse.editors/-i-x-m-l-color-constants/index.html) |  |
| [com.virtlink.aesi.eclipse.IconManager](../com.virtlink.aesi.eclipse/-icon-manager/index.html) | Manages the icons. |
| [org.slf4j.impl.LoggerBase](../org.slf4j.impl/-logger-base/index.html) | Base implementation for loggers. |
| [com.virtlink.aesi.eclipse.logging.LoggerFactory](../com.virtlink.aesi.eclipse.logging/-logger-factory/index.html) |  |
| [com.virtlink.aesi.debugging.mock.MockBreakpoint](../com.virtlink.aesi.debugging.mock/-mock-breakpoint/index.html) |  |
| [com.virtlink.aesi.debugging.mock.MockDebugSession](../com.virtlink.aesi.debugging.mock/-mock-debug-session/index.html) |  |
| [com.virtlink.aesi.debugging.mock.MockDebugger](../com.virtlink.aesi.debugging.mock/-mock-debugger/index.html) |  |
| [com.virtlink.aesi.debugging.mock.MockThread](../com.virtlink.aesi.debugging.mock/-mock-thread/index.html) |  |
| [com.virtlink.aesi.NonNullByDefault](../com.virtlink.aesi/-non-null-by-default/index.html) | This annotation can be applied to a package, class or method to indicate that the class fields, method return types and parameters in that element are not null by default unless there is:
* An explicit nullness annotation
* The method overrides a method in a superclass (in which case the annotation of the corresponding parameter in the superclass applies)
*  there is a default parameter annotation applied to a more tightly nested element.
<br> |
| [com.virtlink.aesi.eclipse.editors.NonRuleBasedDamagerRepairer](../com.virtlink.aesi.eclipse.editors/-non-rule-based-damager-repairer/index.html) |  |
| [com.virtlink.aesi.Offset](../com.virtlink.aesi/-offset/index.html) | A location in a document. |
| [com.virtlink.aesi.Span2](../com.virtlink.aesi/-span2/index.html) | A span in a document. |
| [org.slf4j.impl.StaticLoggerBinder](../org.slf4j.impl/-static-logger-binder/index.html) | The binding of LoggerFactory class with an actual instance of ILoggerFactory is performed using information returned by this class. |
| [com.virtlink.aesi.structureoutline.Symbol](../com.virtlink.aesi.structureoutline/-symbol/index.html) |  |
| [com.virtlink.aesi.structureoutline.SymbolKind](../com.virtlink.aesi.structureoutline/-symbol-kind/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.TagRule](../com.virtlink.aesi.eclipse.editors/-tag-rule/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.TextStyle](../com.virtlink.aesi.eclipse.editors/-text-style/index.html) | Describes the style of source code. |
| [com.virtlink.aesi.debugging.ThreadState](../com.virtlink.aesi.debugging/-thread-state/index.html) | Specifies the state of a thread. |
| [com.virtlink.aesi.syntaxhighlighting.Token](../com.virtlink.aesi.syntaxhighlighting/-token/index.html) | A colorizer token. |
| [com.virtlink.aesi.eclipse.editors.XMLPartitionScanner](../com.virtlink.aesi.eclipse.editors/-x-m-l-partition-scanner/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.XMLScanner](../com.virtlink.aesi.eclipse.editors/-x-m-l-scanner/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.XMLTagScanner](../com.virtlink.aesi.eclipse.editors/-x-m-l-tag-scanner/index.html) |  |
| [com.virtlink.aesi.eclipse.editors.XMLWhitespaceDetector](../com.virtlink.aesi.eclipse.editors/-x-m-l-whitespace-detector/index.html) |  |

