---
title: IDebugSession - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](.)

# IDebugSession

`interface IDebugSession`

A debug session.

### Functions

| [addDebugSessionListener](add-debug-session-listener.html) | `abstract fun addDebugSessionListener(listener: IDebugSessionListener): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Adds a debug session listener. |
| [getChildVariables](get-child-variables.html) | `abstract fun getChildVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope, variable: IVariable): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`<br>Gets the child variables for the variable. This method is only called while execution is suspended. |
| [getScopes](get-scopes.html) | `abstract fun getScopes(thread: IAesiThread, stackFrame: IAesiStackFrame): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IScope>`<br>Gets the scopes for the specified stack frame. Even when an implementation doesn't support scopes, they must at least return one (dummy) scope. This method is only called while execution is suspended. |
| [getStackFrames](get-stack-frames.html) | `abstract fun getStackFrames(thread: IAesiThread): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiStackFrame>`<br>Gets the stack frames for the specified thread. Even when an implementation doesn't support stack frames, they must at least return one (dummy) stack frame. This method is only called while execution is suspended. |
| [getThreads](get-threads.html) | `abstract fun getThreads(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiThread>`<br>Gets the threads of the debuggee. Even when an implementation doesn't support (multiple) threads, they must at least return one (dummy) thread. |
| [getVariables](get-variables.html) | `abstract fun getVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`<br>Gets the variables for the specified scope. This method is only called while execution is suspended. |
| [initialize](initialize.html) | `abstract fun initialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Initialize the session. This method must be non-blocking. When initialization has been completed, the IDebugSessionListener#onThreadInitialized event must be fired. |
| [removeDebugSessionListener](remove-debug-session-listener.html) | `abstract fun removeDebugSessionListener(listener: IDebugSessionListener): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Removes a debug session listener. |
| [resume](resume.html) | `abstract fun resume(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Resume execution. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadResumed`](#) event must be fired. |
| [skipTo](skip-to.html) | `abstract fun skipTo(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Changes the current execution point. |
| [step](step.html) | `abstract fun step(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Step forward. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadResumed`](#) event must be fired. When execution then arrives at the desired position and is suspended, the ``[`IDebugSessionListener#onThreadSuspended`](#) event must be fired. |
| [stepIn](step-in.html) | `abstract fun stepIn(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Step into the child scope. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadResumed`](#) event must be fired. When execution then arrives at the desired position and is suspended, the ``[`IDebugSessionListener#onThreadSuspended`](#) event must be fired. |
| [stepOut](step-out.html) | `abstract fun stepOut(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Step out of the current scope. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadResumed`](#) event must be fired. When execution then arrives at the desired position and is suspended, the ``[`IDebugSessionListener#onThreadSuspended`](#) event must be fired. |
| [suspend](suspend.html) | `abstract fun suspend(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Suspend execution. This method must be non-blocking. When execution has been suspended, the ``[`IDebugSessionListener#onThreadSuspended`](#) event must be fired. |
| [terminate](terminate.html) | `abstract fun terminate(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Terminates execution. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadTerminated`](#) event must be fired. |

