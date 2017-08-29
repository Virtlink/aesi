---
title: AesiTokenTypeManager.<init> - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.syntaxhighlighting](../index.html) / [AesiTokenTypeManager](index.html) / [&lt;init&gt;](.)

# &lt;init&gt;

`AesiTokenTypeManager()`

Tracks token types.

IntelliJ can't handle too many token types. However, we'll still need a different token type for
each different style. This class returns a token type that matches the style, if found; otherwise,
creates a new token type and stores it for re-use.

The token type manager is specific to a single language.

