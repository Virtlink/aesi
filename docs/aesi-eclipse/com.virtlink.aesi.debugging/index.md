---
title: com.virtlink.aesi.debugging - aesi-eclipse
---

[aesi-eclipse](../index.html) / [com.virtlink.aesi.debugging](.)

## Package com.virtlink.aesi.debugging

### Types

| [AbstractDebugger](-abstract-debugger/index.html) | `abstract class AbstractDebugger : IDebugger`<br>Abstract base class for debuggers. The debugger must call the ``[`#fireThreadCreated`](#) and ``[`#fireThreadDestroyed`](#) methods for any threads that are created or destroyed after the debugger has been attached and before the debugger has been detached. Then, for each thread created, the debugger must call either the ``[`#fireThreadResumed`](#) or the ``[`#fireThreadSuspended`](#) methods to indicate the running state of the thread. |
| [AbstractSyncDebugger](-abstract-sync-debugger/index.html) | `abstract class AbstractSyncDebugger : AbstractAsyncDebugger`<br>Executes synchronous actions asynchronously. |
| [IAttachOptions](-i-attach-options/index.html) | `interface IAttachOptions`<br>Options for attaching to a program. |
| [IDebugSession](-i-debug-session/index.html) | `interface IDebugSession`<br>A debug session. |
| [IDebuggerCapabilities](-i-debugger-capabilities/index.html) | `interface IDebuggerCapabilities`<br>Specifies the capabilities of the debugger. |
| [IEvaluator](-i-evaluator/index.html) | `interface IEvaluator`<br>Evaluates expressions. |
| [IProcess](-i-process.html) | `interface IProcess`<br>Created by daniel on 6/29/17. |
| [IValue](-i-value.html) | `interface IValue`<br>A value. |
| [ThreadState](-thread-state/index.html) | `class ThreadState`<br>Specifies the state of a thread. |

