---
title: IDebugSession.step - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [step](.)

# step

`abstract fun step(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Step forward. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadResumed`](#) event must be fired. When execution then arrives at the desired position and is suspended, the ``[`IDebugSessionListener#onThreadSuspended`](#) event must be fired.

### Parameters

`thread` - The thread to step.