package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.editorservices.syntaxcoloring.IToken
//import com.virtlink.paplj.syntax.testBaseListener

class AntlrSyntaxColorizer: ISyntaxColorer {
    override fun highlight(project: IProject, document: IDocument, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    class X: testBaseListener {
//
//    }
}