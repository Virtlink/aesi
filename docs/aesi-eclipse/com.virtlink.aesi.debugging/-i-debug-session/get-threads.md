---
title: IDebugSession.getThreads - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [getThreads](.)

# getThreads

`abstract fun getThreads(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiThread>`

Gets the threads of the debuggee. Even when an implementation doesn't support (multiple) threads, they must at least return one (dummy) thread.

**Return**
A list of threads.

