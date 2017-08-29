---
title: IDebugSession.resume - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [resume](.)

# resume

`abstract fun resume(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Resume execution. This method must be non-blocking. When execution has been resumed, the ``[`IDebugSessionListener#onThreadResumed`](#) event must be fired.

### Parameters

`thread` - The thread to resume.