package org.metaborg.paplj.codecompletion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.AutoCompletionPolicy
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectUtil
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.util.PlatformIcons
import com.intellij.util.ProcessingContext
import com.intellij.util.VisibilityIcons
import com.virtlink.editorservices.codecompletion.ICodeCompleter
import org.metaborg.paplj.PapljLanguage
import org.metaborg.paplj.psi.PapljTypes
import com.intellij.ui.RowIcon
import com.intellij.util.ui.EmptyIcon
import com.virtlink.editorservices.*
import com.virtlink.editorservices.codecompletion.Attribute
import java.util.*
import javax.swing.text.StyleConstants.setIcon
import com.intellij.ui.LayeredIcon
import com.intellij.util.SmartList
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import javax.swing.Icon


class PapljCompletionContributor : CompletionContributor() {

    // TODO: Inject
    val codeCompleter = PapljCodeCompleter()

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val smart = when (parameters.completionType) {
            CompletionType.BASIC -> false
            CompletionType.SMART -> true
            CompletionType.CLASS_NAME -> return
        }

        // TODO: More sensible translation from IntelliJ project to Aesi Project.
        val project = com.virtlink.editorservices.Project(parameters.originalFile.project.name)
        // TODO: More sensible translation from IntelliJ file to relative path; or another way to uniquely identify a file
        val document = Document(getModuleRelativePath(parameters.originalFile) ?: "")
        val offset = parameters.offset

        val proposals = codeCompleter.complete(project, DocumentLocation(document, offset), smart, CancellationToken())
        for (proposal in proposals) {
            val icon = getIcon(proposal.kind, proposal.visibility, proposal.attributes)
            // TODO: Prefix?
            val element = LookupElementBuilder.create(proposal.name)
                    .withInsertHandler({ context, item -> proposalInsertHandler(context, item, proposal) })
                    .withTailText(proposal.postfix, true)
                    .withTypeText(proposal.type)
                    .withCaseSensitivity(proposal.caseSensitive)
//                    .withPresentableText(proposal.prefix + proposal.name)
                    .withIcon(icon)
                    .withBoldness(proposal.attributes.contains(Attribute.NotInherited))
                    .withStrikeoutness(proposal.attributes.contains(Attribute.Deprecated))
            // TODO: Description
            // TODO: Documentation
            val priorityElement = PrioritizedLookupElement.withPriority(element, proposal.priority.toDouble())
            result.addElement(priorityElement)
        }
    }

    private fun proposalInsertHandler(context: InsertionContext, item: LookupElement, proposal: ICompletionProposal) {
        // Replace the text that's normally inserted by the completion.
        val insertionText = proposal.insertionText ?: proposal.name
        context.document.replaceString(context.startOffset, context.tailOffset, insertionText)
        val caret = proposal.caret
        if (caret != null && caret > 0 && caret < insertionText.length) {
            // Move the cursor relative to the end of the replaced text.
            EditorModificationUtil.moveCaretRelatively(context.editor, caret - insertionText.length)
        }
    }

    private fun getIcon(kind: Kind, visibility: IVisibility?, attributes: EnumSet<Attribute>) : Icon? {
        val kindIcon = getKindIcon(kind)
        val visibilityIcon = getVisibilityIcon(visibility)
        val baseIcon = getBaseIcon(kindIcon, attributes)

        if (baseIcon == null && visibilityIcon == null)
            return null

        val resultIcon = RowIcon(2)
        resultIcon.setIcon(baseIcon ?: EmptyIcon.create(PlatformIcons.CLASS_ICON), 0)
        resultIcon.setIcon(visibilityIcon ?: EmptyIcon.create(PlatformIcons.PUBLIC_ICON), 1)
        return resultIcon
    }

    private fun getKindIcon(kind: Kind): Icon? = when (kind) {
        Kind.Variable -> PlatformIcons.VARIABLE_ICON
        Kind.Parameter -> PlatformIcons.PARAMETER_ICON
        Kind.Field -> PlatformIcons.FIELD_ICON
        Kind.Property -> PlatformIcons.PROPERTY_ICON
        Kind.Function -> PlatformIcons.FUNCTION_ICON
        Kind.Method -> PlatformIcons.METHOD_ICON
        Kind.AbstractMethod -> PlatformIcons.ABSTRACT_METHOD_ICON
        Kind.Interface -> PlatformIcons.INTERFACE_ICON
        Kind.Class -> PlatformIcons.CLASS_ICON
        Kind.AbstractClass -> PlatformIcons.ABSTRACT_CLASS_ICON
//        Kind.Trait -> TODO()
        Kind.Exception -> PlatformIcons.EXCEPTION_CLASS_ICON
        Kind.Enum -> PlatformIcons.ENUM_ICON
        Kind.Annotation -> PlatformIcons.ANNOTATION_TYPE_ICON
        Kind.Package -> PlatformIcons.PACKAGE_ICON
        else -> null
    }

    private fun getVisibilityIcon(visibility: IVisibility?): Icon? {
        if (visibility == null)
            return null

        return when (visibility.classVisiblity) {
            ClassVisibility.Public -> when (visibility.packageVisibility) {
                PackageVisibility.Public -> when (visibility.libraryVisibility) {
                    LibraryVisibility.Public -> PlatformIcons.PUBLIC_ICON
                    LibraryVisibility.Friend -> PlatformIcons.PUBLIC_ICON           // By lack of a better icon.
                    LibraryVisibility.Internal -> PlatformIcons.PACKAGE_LOCAL_ICON  // By lack of a better icon.
                }
                PackageVisibility.Package -> when (visibility.libraryVisibility) {
                    LibraryVisibility.Public -> PlatformIcons.PACKAGE_LOCAL_ICON
                    LibraryVisibility.Friend -> PlatformIcons.PACKAGE_LOCAL_ICON    // By lack of a better icon.
                    LibraryVisibility.Internal -> PlatformIcons.PACKAGE_LOCAL_ICON  // By lack of a better icon.
                }
            }
            ClassVisibility.Protected -> when (visibility.packageVisibility) {
                PackageVisibility.Public -> when (visibility.libraryVisibility) {
                    LibraryVisibility.Public -> PlatformIcons.PROTECTED_ICON
                    LibraryVisibility.Friend -> PlatformIcons.PROTECTED_ICON       // By lack of a better icon.
                    LibraryVisibility.Internal -> PlatformIcons.PROTECTED_ICON     // By lack of a better icon.
                }
                PackageVisibility.Package -> when (visibility.libraryVisibility) {
                    LibraryVisibility.Public -> PlatformIcons.PROTECTED_ICON       // By lack of a better icon.
                    LibraryVisibility.Friend -> PlatformIcons.PROTECTED_ICON       // By lack of a better icon.
                    LibraryVisibility.Internal -> PlatformIcons.PROTECTED_ICON     // By lack of a better icon.
                }
            }
            ClassVisibility.Private -> PlatformIcons.PRIVATE_ICON
        }
    }

    fun getBaseIcon(kindIcon: Icon?, attributes: EnumSet<Attribute>): Icon? {
        if (kindIcon == null) return null

        val iconLayers = SmartList<Icon>()

        if (attributes.contains(Attribute.External)) {
            iconLayers.add(PlatformIcons.LOCKED_ICON)
        }
        if (attributes.contains(Attribute.Excluded)) {
            iconLayers.add(PlatformIcons.EXCLUDED_FROM_COMPILE_ICON)
        }
        if (attributes.contains(Attribute.Static)) {
            iconLayers.add(AllIcons.Nodes.StaticMark)
        }
        if (attributes.contains(Attribute.Test)) {
            // Currently has no icon.
        }

        return if (!iconLayers.isEmpty()) {
            val layeredIcon = LayeredIcon(1 + iconLayers.size)
            layeredIcon.setIcon(kindIcon, 0)
            for (i in 0..iconLayers.size - 1) {
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