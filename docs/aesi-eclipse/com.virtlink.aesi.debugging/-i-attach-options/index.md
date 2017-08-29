---
title: IAttachOptions - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IAttachOptions](.)

# IAttachOptions

`interface IAttachOptions`

Options for attaching to a program.

### Functions

| [getUri](get-uri.html) | `abstract fun getUri(): `[`URI`](http://docs.oracle.com/javase/6/docs/api/java/net/URI.html)<br>Gets the program's URI. The interpretation of this URI is left to the debugger. It may signify an executable, a running process, the root folder of a project, etc. The URI is set by the editor's language plugin, such that the editor's plugin and the debugger can agree on what kinds of programs and URIs to support. The decision whether to launch a program or attach to an already running program is also determined by the interpretation of this URI. For example, an URI denoting a process or socket indicates that the debugger should be attached, whereas an executable or program root folder indicates that the debugger should launch the program as well. |
| [shouldSuspend](should-suspend.html) | `abstract fun shouldSuspend(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Gets whether the program should be running or suspended when the debugger is attached. |

