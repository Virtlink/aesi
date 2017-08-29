---
title: NonNullByDefault.<init> - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi](../index.html) / [NonNullByDefault](index.html) / [&lt;init&gt;](.)

# &lt;init&gt;

`NonNullByDefault()`

This annotation can be applied to a package, class or method to indicate that the class fields, method return types and parameters in that element are not null by default unless there is:
* An explicit nullness annotation
* The method overrides a method in a superclass (in which case the annotation of the corresponding parameter in the superclass applies)
*  there is a default parameter annotation applied to a more tightly nested element.

