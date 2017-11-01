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
            val icon = getIcon(proposal.kind, proposal.attributes)
            // TODO: Prefix?
            // TODO: Type
            val element = LookupElementBuilder.create(proposal.label)
                    .withInsertHandler({ context, item -> proposalInsertHandler(context, item, proposal) })
//                    .withTailText(proposal.postfix, true)
//                    .withTypeText(proposal.type)
//                    .withCaseSensitivity(proposal.caseSensitive)
//                    .withPresentableText(proposal.prefix + proposal.name)
                    .withIcon(icon)
                    .withBoldness("not-inherited" in proposal.attributes)
                    .withStrikeoutness("deprecated" in proposal.attributes)
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
        val insertionText = proposal.insertionText ?: proposal.label
        context.document.replaceString(context.startOffset, context.tailOffset, insertionText)
        val caret = proposal.caret
        if (caret != null && caret > 0L && caret < insertionText.length) {
            // Move the cursor relative to the end of the replaced text.
            EditorModificationUtil.moveCaretRelatively(context.editor, (caret - insertionText.length).toInt())
        }
    }

    private fun getIcon(kind: String?, attributes: Collection<String>) : Icon? {
        val kindIcon = getKindIcon(kind)
        val visibilityIcon = getVisibilityIcon(attributes)
        val baseIcon = getBaseIcon(kindIcon, attributes)

        if (baseIcon == null && visibilityIcon == null)
            return null

        val resultIcon = RowIcon(2)
        resultIcon.setIcon(baseIcon ?: EmptyIcon.create(PlatformIcons.CLASS_ICON), 0)
        resultIcon.setIcon(visibilityIcon ?: EmptyIcon.create(PlatformIcons.PUBLIC_ICON), 1)
        return resultIcon
    }

    private fun getKindIcon(kind: String?): Icon? = when (kind) {
        // TODO: Replace some of these by attributes
        "variable" -> PlatformIcons.VARIABLE_ICON
        "parameter" -> PlatformIcons.PARAMETER_ICON
        "field" -> PlatformIcons.FIELD_ICON
        "property" -> PlatformIcons.PROPERTY_ICON
        "function" -> PlatformIcons.FUNCTION_ICON
        "method" -> PlatformIcons.METHOD_ICON
        "abstractMethod" -> PlatformIcons.ABSTRACT_METHOD_ICON
        "interface" -> PlatformIcons.INTERFACE_ICON
        "class" -> PlatformIcons.CLASS_ICON
        "abstractClass" -> PlatformIcons.ABSTRACT_CLASS_ICON
//        Kind.Trait -> TODO()
        "exception" -> PlatformIcons.EXCEPTION_CLASS_ICON
        "enum" -> PlatformIcons.ENUM_ICON
        "annotation" -> PlatformIcons.ANNOTATION_TYPE_ICON
        "package" -> PlatformIcons.PACKAGE_ICON
        else -> null
    }

    private fun getVisibilityIcon(attributes: Collection<String>): Icon? {
        return when {
            "public" in attributes -> PlatformIcons.PUBLIC_ICON
            "package" in attributes -> PlatformIcons.PACKAGE_LOCAL_ICON
            "protected" in attributes -> PlatformIcons.PROTECTED_ICON
            "private" in attributes -> PlatformIcons.PRIVATE_ICON
            else -> null
        }
    }

    private fun getBaseIcon(kindIcon: Icon?, attributes: Collection<String>): Icon? {
        if (kindIcon == null) return null

        val iconLayers = SmartList<Icon>()

        if ("external" in attributes) {
            iconLayers.add(PlatformIcons.LOCKED_ICON)
        }
        if ("excluded" in attributes) {
            iconLayers.add(PlatformIcons.EXCLUDED_FROM_COMPILE_ICON)
        }
        if ("static" in attributes) {
            iconLayers.add(AllIcons.Nodes.StaticMark)
        }
        if ("test" in attributes) {
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