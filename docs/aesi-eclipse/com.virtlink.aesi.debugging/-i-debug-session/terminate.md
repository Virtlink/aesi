---
title: IDebugSession.terminate - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [terminate](.)

# terminate

`abstract fun terminate(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Terminates execution. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadTerminated`](#) event must be fired.

### Parameters

`thread` - The thread to terminate.