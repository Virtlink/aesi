---
title: AbstractDebugger - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [AbstractDebugger](.)

# AbstractDebugger

`abstract class AbstractDebugger : IDebugger`

Abstract base class for debuggers. The debugger must call the ``[`#fireThreadCreated`](#) and ``[`#fireThreadDestroyed`](#) methods for any threads that are created or destroyed after the debugger has been attached and before the debugger has been detached. Then, for each thread created, the debugger must call either the ``[`#fireThreadResumed`](#) or the ``[`#fireThreadSuspended`](#) methods to indicate the running state of the thread.

### Functions

| [addListener](add-listener.html) | `fun addListener(listener: IDebuggerListener): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [attach](attach.html) | `fun attach(arguments: `[`IAttachOptions`](../-i-attach-options/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [canDetach](can-detach.html) | `open fun canDetach(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Gets whether the debugger can detach. |
| [detach](detach.html) | `fun detach(arguments: IDetachOptions): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [getThreads](get-threads.html) | `fun getThreads(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiThread>`<br>{@inheritDoc} |
| [initialize](initialize.html) | `fun initialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [removeListener](remove-listener.html) | `fun removeListener(listener: IDebuggerListener): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [resume](resume.html) | `fun resume(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [step](step.html) | `fun step(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepIn](step-in.html) | `fun stepIn(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepOut](step-out.html) | `fun stepOut(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [suspend](suspend.html) | `fun suspend(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [terminate](terminate.html) | `fun terminate(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |

