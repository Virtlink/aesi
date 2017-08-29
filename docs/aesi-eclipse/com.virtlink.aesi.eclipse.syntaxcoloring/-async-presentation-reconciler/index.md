---
title: AsyncPresentationReconciler - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.eclipse.syntaxcoloring](../index.html) / [AsyncPresentationReconciler](.)

# AsyncPresentationReconciler

`open class AsyncPresentationReconciler : AbstractPresentationReconciler`

Implementation of the `IPresentationReconciler` that assumes that its presentation repairer may take some time. The damagers however should be quick, since they'll be executed on the main thread.

### Constructors

| [&lt;init&gt;](-init-.html) | `AsyncPresentationReconciler()`<br>Implementation of the `IPresentationReconciler` that assumes that its presentation repairer may take some time. The damagers however should be quick, since they'll be executed on the main thread. |

