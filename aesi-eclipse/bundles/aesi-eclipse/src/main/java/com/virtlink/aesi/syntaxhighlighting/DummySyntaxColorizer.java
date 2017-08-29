package com.virtlink.aesi.syntaxhighlighting;

import java.util.ArrayList;
import java.util.List;

import com.virtlink.aesi.*;

import javax.annotation.Nullable;

/**
 * A dummy implementation of the syntax colorizer interface.
 */
public class DummySyntaxColorizer implements ISyntaxColorizer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IToken> colorize(@Nullable IAesiDocument document,
								 @Nullable String text,
								 @Nullable Span span,
								 @Nullable ICancellationToken cancellationToken) {
		ArrayList<IToken> tokens = new ArrayList<IToken>();
		tokens.add(new Token(Span.of(2, 4), "keyword"));
		tokens.add(new Token(Span.of(12, 4), "keyword"));
		return tokens;
	}
}
