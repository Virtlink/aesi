---
title: IDebugSession.initialize - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [initialize](.)

# initialize

`abstract fun initialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Initialize the session. This method must be non-blocking. When initialization has been completed, the IDebugSessionListener#onThreadInitialized event must be fired.

