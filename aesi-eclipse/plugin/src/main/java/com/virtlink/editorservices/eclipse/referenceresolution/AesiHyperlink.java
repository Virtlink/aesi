package com.virtlink.editorservices.eclipse.referenceresolution;

import javax.annotation.Nullable;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.Span;
import com.virtlink.editorservices.eclipse.editor.EditorUtils;
import com.virtlink.editorservices.eclipse.resources.EclipseResourceManager;
import com.virtlink.editorservices.symbols.ISymbol;

public class AesiHyperlink implements IHyperlink {

	/**
	 * Factory for the {@link AesiHyperlink} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link AesiHyperlink}.
		 * 
	     * @param symbol The symbol.
	     * @param type The type; or null.
	     * @param region The region.
		 * @return The content provider.
		 */
		AesiHyperlink create(ISymbol symbol, @Nullable String type, IRegion region);
	}
	
	private final ISymbol symbol;
	@Nullable private final String type;
	private final IRegion region;
	
	private final EclipseResourceManager resourceManager;

	@Override
	public String getHyperlinkText() {
		return this.symbol.getLabel();
	}

	@Override
	public String getTypeLabel() {
		return this.type;
	}
	
	@Override
	public IRegion getHyperlinkRegion() {
		return this.region;
	}
	
	@Inject
	public AesiHyperlink(
			@Assisted final ISymbol symbol,
			@Assisted @Nullable final String type,
			@Assisted final IRegion region,
			final EclipseResourceManager resourceManager
	) {
		this.symbol = symbol;
		this.type = type;
		this.region = region;
		this.resourceManager = resourceManager;
	}

	@Override
	public void open() {
		IFile file = this.resourceManager.getFile(this.symbol.getResource());
		Span highlight = this.symbol.getNameRange();
		EditorUtils.openFileAsync(file, highlight, true);
	}
	
}