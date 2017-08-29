package com.virtlink.aesi.syntaxhighlighting;

import java.util.List;

import com.virtlink.aesi.*;
import javax.annotation.Nullable;

/**
 * A syntax colorizer.
 */
public interface ISyntaxColorizer {
	
	/**
	 * Colorizes the text in a document.
	 * 
	 * The tokens must be returned in the order they occur in the document, and it is
     * permitted to return tokens that lie outside the specified span or tokens that cross
     * the span boundaries. In particular, if the document span intersects a token, the whole
     * token must be returned. Gaps between tokens are permitted (e.g. whitespace) but not required.
 	 * Text for which no token was returned get the default (unstyled) scope.
	 *
	 * When {@code text} is not {@code null}, it must be used since it may be different from the
	 * text that has been stored. When {@code text} is {@code null}, {@code document} is guaranteed
	 * to be non-null and must be used to determine the text.
	 *
	 * The cancellation token is used to abort a long-running operation when the result is no longer needed.
	 * Implementations should listen to the cancellation event and abort, for example by throwing an
	 * exception or returning a dummy result. It is allowed to ignore the cancellation event, but
	 * this may negatively impact performance.
	 *
	 * @param document The document to colorize; or {@code null} when not known.
	 * @param text The text of the document to colorize; or {@code null} when not specified.
	 * @param span The part of the document to colorize; or {@code null} to colorize the whole document.
	 * @param cancellationToken The cancellation token; or {@code null} when not supported.
	 * @return The list of tokens in the colorized document.
	 */
	List<IToken> colorize(@Nullable IAesiDocument document,
						  @Nullable String text,
						  @Nullable Span span,
						  @Nullable ICancellationToken cancellationToken);
}
