package com.virtlink.editorservices.eclipse.syntaxcoloring;

import java.util.List;

import com.google.inject.Inject;
import com.virtlink.editorservices.eclipse.DummyDocument;
import com.virtlink.editorservices.eclipse.DummyProject;
import com.virtlink.editorservices.eclipse.logging.LoggerFactory;
import com.virtlink.editorservices.IDocument;
import com.virtlink.editorservices.IProject;
import com.virtlink.editorservices.Offset;
import com.virtlink.editorservices.Span;
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService;
import org.eclipse.core.runtime.ILog;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.RGB;

//import com.virtlink.aesi.eclipse.AesiPlugin;
import com.virtlink.editorservices.syntaxcoloring.IToken;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class AesiSourceScanner implements ITokenScanner {

	private static final Logger log = LoggerFactory.getLogger(AesiSourceScanner.class);

	private final AesiColorManager colorManager;
	private final ISyntaxColoringService colorizer;
	
	private final TextStyle style1 = new TextStyle(new RGB(0, 0, 255));
	private final org.eclipse.jface.text.rules.IToken style1Token;
	private final TextStyle style2 = new TextStyle(new RGB(0, 255, 0));
	private final org.eclipse.jface.text.rules.IToken style2Token;
	
	private org.eclipse.jface.text.IDocument document;
	private List<IToken> aesiTokens;
	private int currentAesiTokenIndex;
	private int bufferStart;
	private int bufferEnd;
	private int currentTokenStart;
	private int currentTokenEnd;
	private org.eclipse.jface.text.rules.IToken currentToken;

	@Inject
	public AesiSourceScanner(
			ISyntaxColoringService colorizer,
			AesiColorManager colorManager
	) {
		this.colorManager = colorManager;
		this.colorizer = colorizer;

		style1Token = new Token(style1.createTextAttribute(this.colorManager));
		style2Token = new Token(style2.createTextAttribute(this.colorManager));
	}
	
	
	@Override
	public void setRange(org.eclipse.jface.text.IDocument document, int offset, int length) {

		log.debug("setRange({}, {})", offset, length);

		
		this.document = document;
		this.bufferStart = offset;
		this.bufferEnd = offset + length;
		
		// TODO: Determine AESI project and AESI document.
		IProject project = new DummyProject();
		IDocument document2 = new DummyDocument();
		Span span = new Span(new Offset(this.bufferStart), new Offset(this.bufferEnd));
		this.aesiTokens = colorizer.getTokens(project, document2, span, null);
		this.currentAesiTokenIndex = 0;
		this.currentTokenStart = offset;
		this.currentTokenEnd = offset;
		this.currentToken = null;
		
		advance();
	}
	
	private void advance() {
		log.debug("advance(), offset: {}", this.currentTokenStart);
		log.debug("advancing, starting at token [{}]", this.currentAesiTokenIndex);

		// The start of the next token is the end of the current token.
        this.currentTokenStart = this.currentTokenEnd;

        if (this.currentTokenStart >= this.bufferEnd) {
            // We've reached the end of the buffer.
            this.currentTokenStart = this.bufferEnd;
            this.currentTokenEnd = this.bufferEnd;
            this.currentToken =  Token.EOF;
            return;
        }

        // Advance through the Aesi tokens to find the first token whose start is at or after the new offset.
        advanceAesiTokensUntil(this.currentTokenStart);

		log.debug("advancing, moved to token [{}]", this.currentAesiTokenIndex);

        // Try to get the next Aesi token and its offset.
        IToken currentAesiToken = tryGetToken(this.currentAesiTokenIndex);
        int currentAesiTokenOffset;
        if (currentAesiToken != null) {
        	currentAesiTokenOffset = currentAesiToken.getLocation().getStart().getValue();
        } else {
        	currentAesiTokenOffset = this.bufferEnd;
        }

		log.debug("token [{}] @ {} = {}", this.currentAesiTokenIndex, currentAesiTokenOffset, currentAesiToken != null ? currentAesiToken.getName() : "-");
        
        if (currentAesiTokenOffset > this.currentTokenStart) {
            // Either there is no new Aesi token, or the new current Aesi token's start offset
            // is past the current token offset,
            // so we produce a whitespace token to fill up the gap.
            this.currentTokenEnd = currentAesiTokenOffset;
            this.currentToken = createDefaultToken();
			log.debug("> default token @ {}-{}", this.currentTokenStart, this.currentTokenEnd);

//            log.debug("Advanced to WS $currentTokenType@$currentTokenStart-$currentTokenEnd");
        } else if (currentAesiToken != null){
            // The new current Aesi token starts right at the current token offset,
            // so we produce a corresponding IntelliJ token type to represent it.
            this.currentTokenEnd = Integer.min(currentAesiToken.getLocation().getEnd().getValue(), this.bufferEnd);
            this.currentToken = createToken(currentAesiToken);
			log.debug("> new     token @ {}-{}", this.currentTokenStart, this.currentTokenEnd);

//            log.debug("Advanced to TK $currentTokenType@$currentTokenStart-$currentTokenEnd with Aesi token $currentAesiToken@$currentAesiTokenOffset (${currentAesiToken.location}, ${currentAesiToken.scope})");
        } else {
            throw new IllegalStateException("We've advanced outside the realm of possibilities.");
        }
	}

    private void advanceAesiTokensUntil(int offset) {
        while (this.currentAesiTokenIndex >= 0
                && this.currentAesiTokenIndex < this.aesiTokens.size()
                && this.aesiTokens.get(this.currentAesiTokenIndex).getLocation().getStart().getValue() < offset)
        {
            this.currentAesiTokenIndex += 1;
        }
        // The `currentTokenOffset` is either out of range,
        // or the start offset of the `aesiTokens[currentAesiTokenIndex]`
        // is equal to or greater than the desired offset.
    }

    @Nullable
    private IToken tryGetToken(int index) {
        if (index >= 0 && index < this.aesiTokens.size())
            return this.aesiTokens.get(index);
        else
            return null;
    }
    
    private org.eclipse.jface.text.rules.IToken createToken(IToken aesiToken) {
    	return this.style1Token;
    }
    
    private org.eclipse.jface.text.rules.IToken createDefaultToken() {
    	return Token.UNDEFINED;
//    	return this.style2Token;
    }
    
	@Override
	public org.eclipse.jface.text.rules.IToken nextToken() {
		org.eclipse.jface.text.rules.IToken token = this.currentToken;
        
		advance();
		
		return token;
	}

	@Override
	public int getTokenOffset() {
		return this.currentTokenStart;
	}

	@Override
	public int getTokenLength() {
		return this.currentTokenEnd - this.currentTokenStart;
	}

}
