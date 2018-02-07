package com.virtlink.editorservices.intellij.codecompletion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiFile
import com.intellij.util.PlatformIcons
import com.intellij.ui.RowIcon
import com.intellij.util.ui.EmptyIcon
import com.intellij.ui.LayeredIcon
import com.intellij.util.SmartList
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import javax.swing.Icon
import com.google.inject.Inject
import com.virtlink.editorservices.NullCancellationToken
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.ScopeNames
import com.virtlink.editorservices.codecompletion.CompletionProposal
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.intellij.resources.IntellijResourceManager


abstract class AesiCompletionContributor
@Inject constructor(private val codeCompleter: ICodeCompletionService,
                    private val resourceManager: IntellijResourceManager)
    : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val smart = when (parameters.completionType) {
            CompletionType.BASIC -> false
            CompletionType.SMART -> return
            CompletionType.CLASS_NAME -> return
        }

        val documentUri = this.resourceManager.getUri(parameters.originalFile)
        val offset = parameters.offset.toLong()

        val completionInfo = this.codeCompleter.getCompletionInfo(documentUri, offset, NullCancellationToken) ?: return

        // IntelliJ by default uses the CamelHumpMatcher to test whether a completion result
        // should be included. However, this matcher takes the start of the current element
        // up to the current value as the prefix. With the plain-text-like PSI file we have
        // only a single element for the whole file, which means it will take the whole file's
        // text before the caret position as the prefix. We don't want that, so we'll use our
        // own prefix matcher.
        // Since the prefix matcher determines what is shown, highlights the prefix in the
        // completion list and replaces it by the inserted text, we need to make sure the prefix
        // is correct. Also, if there is only one result with the given prefix, it is inserted
        // automagically.
        // When trying to insert something on an empty line, the prefix is already "".
        // In that case we reuse the existing prefix matcher, as our matcher substitute has a bug
        // where it thinks it has matched the first character of the results already when set to "".

        // FIXME
//        val newResult = if (result.prefixMatcher.prefix != "") result.withPrefixMatcher(PlainPrefixMatcher(completionInfo.prefix)) else result


        for (proposal in completionInfo.proposals) {
            val icon = getIcon(proposal.scopes)
            // TODO: Prefix?
            // TODO: Type
            val element = LookupElementBuilder.create(proposal.label)
                    .withInsertHandler({ context, item -> proposalInsertHandler(context, item, proposal) })
//                    .withTailText(proposal.postfix, true)
//                    .withTypeText(proposal.type)
//                    .withCaseSensitivity(proposal.caseSensitive)
//                    .withPresentableText(proposal.prefix + proposal.name)
                    .withIcon(icon)
                    .withBoldness("meta.not-inherited" in proposal.scopes)
                    .withStrikeoutness("meta.deprecated" in proposal.scopes)
            // TODO: Description
            // TODO: Documentation
//            val priorityElement = PrioritizedLookupElement.withPriority(element, proposal.priority.toDouble())
//            result.addElement(priorityElement)
            // FIXME
//            newResult.addElement(element)
            result.addElement(element)
        }
    }

    private fun proposalInsertHandler(context: InsertionContext, item: LookupElement, proposal: ICompletionProposal) {
        // Replace the text that's normally inserted by the completion.
        val insertionText = proposal.content ?: proposal.label
        context.document.replaceString(context.startOffset, context.tailOffset, insertionText)
        val caret: Offset? = (proposal as? CompletionProposal)?.caret
        if (caret != null && caret > 0L && caret < insertionText.length) {
            // Move the cursor relative to the end of the replaced text.
            EditorModificationUtil.moveCaretRelatively(context.editor, (caret - insertionText.length).toInt())
        }
    }

    private fun getIcon(scopes: ScopeNames) : Icon? {
        val kindIcon = getKindIcon(scopes)
        val visibilityIcon = getVisibilityIcon(scopes)
        val baseIcon = getBaseIcon(kindIcon, scopes)

        if (baseIcon == null && visibilityIcon == null)
            return null

        val resultIcon = RowIcon(2)
        resultIcon.setIcon(baseIcon ?: EmptyIcon.create(PlatformIcons.CLASS_ICON), 0)
        resultIcon.setIcon(visibilityIcon ?: EmptyIcon.create(PlatformIcons.PUBLIC_ICON), 1)
        return resultIcon
    }

    private fun getKindIcon(scopes: ScopeNames): Icon? {
        return when {
            "meta.variable" in scopes -> PlatformIcons.VARIABLE_ICON
            "meta.field" in scopes -> PlatformIcons.FIELD_ICON
            "meta.property" in scopes -> PlatformIcons.PROPERTY_ICON
            "meta.function" in scopes -> PlatformIcons.FUNCTION_ICON
            "meta.method" in scopes -> if ("meta.abstract" in scopes) PlatformIcons.ABSTRACT_METHOD_ICON else PlatformIcons.METHOD_ICON
            "meta.interface" in scopes -> PlatformIcons.INTERFACE_ICON
            "meta.class" in scopes -> if ("meta.abstract" in scopes) PlatformIcons.ABSTRACT_CLASS_ICON else PlatformIcons.CLASS_ICON
            "meta.exception" in scopes -> PlatformIcons.EXCEPTION_CLASS_ICON
            "meta.enum" in scopes -> PlatformIcons.ENUM_ICON
            "meta.annotation" in scopes -> PlatformIcons.ANNOTATION_TYPE_ICON
            "meta.package" in scopes -> PlatformIcons.PACKAGE_ICON
            else -> null
        }
    }

    private fun getVisibilityIcon(scopes: ScopeNames): Icon? {
        return when {
            "meta.public" in scopes -> PlatformIcons.PUBLIC_ICON
            "meta.package" in scopes -> PlatformIcons.PACKAGE_LOCAL_ICON
            "meta.internal" in scopes -> PlatformIcons.PACKAGE_LOCAL_ICON
            "meta.protected" in scopes -> PlatformIcons.PROTECTED_ICON
            "meta.private" in scopes -> PlatformIcons.PRIVATE_ICON
            else -> null
        }
    }

    private fun getBaseIcon(kindIcon: Icon?, scopes: ScopeNames): Icon? {
        if (kindIcon == null) return null

        val iconLayers = SmartList<Icon>()

        if ("meta.external" in scopes) {
            iconLayers.add(PlatformIcons.LOCKED_ICON)
        }
        if ("meta.excluded" in scopes) {
            iconLayers.add(PlatformIcons.EXCLUDED_FROM_COMPILE_ICON)
        }
        if ("meta.static" in scopes) {
            iconLayers.add(AllIcons.Nodes.StaticMark)
        }
        if ("meta.test" in scopes) {
            // Currently has no icon.
        }

        return if (!iconLayers.isEmpty()) {
            val layeredIcon = LayeredIcon(1 + iconLayers.size)
            layeredIcon.setIcon(kindIcon, 0)
            for (i in 0 until iconLayers.size) {
                layeredIcon.setIcon(iconLayers[i], i + 1)
            }
            layeredIcon
        } else {
            kindIcon
        }
    }

    /**
     * Gets the path of the PSI file relative to a content root (usually the module).
     *
     * @param file The PSI file.
     * @return The relative path, or null if it could not be determined.
     */
    private fun getModuleRelativePath(file: PsiFile): String? {
        val module = ModuleUtil.findModuleForPsiElement(file)
        var result: String? = null
        if (module == null) return null
        val rootManager = ModuleRootManager.getInstance(module)
        for (root in rootManager.contentRoots) {
            val rootFile = root.canonicalFile ?: continue
            val relativePath = VfsUtil.getRelativePath(file.virtualFile, rootFile) ?: continue
            if (result != null) {
                // Only when we're sure this can never happen,
                // can we break out of the loop once we find our first result.
                throw IllegalStateException("Path found relative to more than one content root.")
            }
            result = relativePath
        }
        return result
    }
}