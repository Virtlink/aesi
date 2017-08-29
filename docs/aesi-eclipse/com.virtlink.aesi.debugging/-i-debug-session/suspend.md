---
title: IDebugSession.suspend - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [suspend](.)

# suspend

`abstract fun suspend(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Suspend execution. This method must be non-blocking. When execution has been suspended, the ``[`IDebugSessionListener#onThreadSuspended`](#) event must be fired.

### Parameters

`thread` - The thread to suspend.