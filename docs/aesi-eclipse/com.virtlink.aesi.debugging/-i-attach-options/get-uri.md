---
title: IAttachOptions.getUri - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IAttachOptions](index.html) / [getUri](.)

# getUri

`abstract fun getUri(): `[`URI`](http://docs.oracle.com/javase/6/docs/api/java/net/URI.html)

Gets the program's URI. The interpretation of this URI is left to the debugger. It may signify an executable, a running process, the root folder of a project, etc. The URI is set by the editor's language plugin, such that the editor's plugin and the debugger can agree on what kinds of programs and URIs to support. The decision whether to launch a program or attach to an already running program is also determined by the interpretation of this URI. For example, an URI denoting a process or socket indicates that the debugger should be attached, whereas an executable or program root folder indicates that the debugger should launch the program as well.

**Return**
The program's URI.

