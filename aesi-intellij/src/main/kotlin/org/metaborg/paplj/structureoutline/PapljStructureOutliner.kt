package org.metaborg.paplj.structureoutline

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Location
import com.virtlink.editorservices.structureoutline.IStructureOutliner
import com.virtlink.editorservices.structureoutline.ISymbol
import com.virtlink.editorservices.structureoutline.SymbolKind

class PapljStructureOutliner: IStructureOutliner {

    private val rootSymbol: SymbolDef = SymbolDef("root", Location(0, 0), SymbolKind.Constant, listOf(
            SymbolDef("file1", Location(0, 0), SymbolKind.File, listOf(
                    SymbolDef("myClass0", Location(0, 2), SymbolKind.Class, listOf(
                            SymbolDef("myFunc", Location(1, 3), SymbolKind.Function, listOf())
                    )),
                    SymbolDef("myClass2", Location(2, 2), SymbolKind.Class, listOf()),
                    SymbolDef("myClass4", Location(4, 2), SymbolKind.Class, listOf())
            )),
            SymbolDef("file2", Location(0, 0), SymbolKind.File, listOf())
    ))

    override fun outline(project: IProject, document: IDocument, symbol: ISymbol?, cancellationToken: ICancellationToken?): List<ISymbol> {
        return if (symbol != null) (symbol as SymbolDef).children else listOf(rootSymbol)
    }

    private data class SymbolDef(
            override val name: String,
            override val location: Location,
            override val kind: SymbolKind,
            val children: List<SymbolDef>) : ISymbol
}