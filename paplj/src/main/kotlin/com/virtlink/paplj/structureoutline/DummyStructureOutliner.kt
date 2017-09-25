package com.virtlink.paplj.structureoutline

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.structureoutline.IStructureOutliner
import com.virtlink.editorservices.structureoutline.ISymbol
import com.virtlink.editorservices.structureoutline.SymbolKind

class DummyStructureOutliner: IStructureOutliner {
    private val rootSymbol: SymbolDef = SymbolDef("root", 0, SymbolKind.Constant, listOf(
            SymbolDef("file1", 0, SymbolKind.File, listOf(
                    SymbolDef("myClass0", 3, SymbolKind.Class, listOf(
                            SymbolDef("myFunc", 7, SymbolKind.Function, listOf())
                    )),
                    SymbolDef("myClass2", 45, SymbolKind.Class, listOf()),
                    SymbolDef("myClass4", 144, SymbolKind.Class, listOf())
            )),
            SymbolDef("file2", 0, SymbolKind.File, listOf())
    ))

    override fun outline(project: IProject, document: IDocument, symbol: ISymbol?, cancellationToken: ICancellationToken?): List<ISymbol> {
        return if (symbol != null) (symbol as SymbolDef).children else listOf(rootSymbol)
    }

    private data class SymbolDef(
            override val name: String,
            override val location: Int,
            override val kind: SymbolKind,
            val children: List<SymbolDef>) : ISymbol

}