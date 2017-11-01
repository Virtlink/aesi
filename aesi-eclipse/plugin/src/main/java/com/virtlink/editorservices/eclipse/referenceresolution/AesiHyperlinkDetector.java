package com.virtlink.editorservices.eclipse.referenceresolution;

import java.net.URI;

import javax.annotation.Nullable;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.NullCancellationToken;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.SpanUtils;
import com.virtlink.editorservices.eclipse.editor.IAesiEditor;
import com.virtlink.editorservices.eclipse.resources.EclipseResourceManager;
import com.virtlink.editorservices.referenceresolution.IDefinition;
import com.virtlink.editorservices.referenceresolution.IReferenceResolutionInfo;
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService;
import com.virtlink.editorservices.symbols.ISymbol;
import com.virtlink.editorservices.types.ITypeProviderService;

public class AesiHyperlinkDetector extends AbstractHyperlinkDetector {

	/**
	 * Factory for the {@link AesiHyperlinkDetector} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link AesiHyperlinkDetector}.
		 * 
	     * @param editor The editor.
		 * @return The content provider.
		 */
		AesiHyperlinkDetector create(IAesiEditor editor);
	}
	
	private final IAesiEditor editor;
	private final EclipseResourceManager resourceManager;
	private final IReferenceResolverService referenceResolverService;
	private final ITypeProviderService typeProviderService;
	private final AesiHyperlink.IFactory hyperlinkFactory;
	
	@Inject
	public AesiHyperlinkDetector(
			@Assisted final IAesiEditor editor,
			final EclipseResourceManager resourceManager,
			final IReferenceResolverService referenceResolverService,
			final ITypeProviderService typeProviderService,
			AesiHyperlink.IFactory hyperlinkFactory
	) {
		Contract.requireNotNull("editor", editor);
		Contract.requireNotNull("resourceManager", resourceManager);
		Contract.requireNotNull("referenceResolverService", referenceResolverService);
		Contract.requireNotNull("typeProviderService", typeProviderService);
		Contract.requireNotNull("hyperlinkFactory", hyperlinkFactory);
		
		this.editor = editor;
		this.resourceManager = resourceManager;
		this.referenceResolverService = referenceResolverService;
		this.typeProviderService = typeProviderService;
		this.hyperlinkFactory = hyperlinkFactory;
	}
	
	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		
		URI document = this.resourceManager.getUri(this.editor);
		long offset = region.getOffset();
		// TODO: Make async.
		IReferenceResolutionInfo resolution = referenceResolverService.resolve(document, offset, null);
		return asHyperlinks(resolution);
	}

	private IHyperlink[] asHyperlinks(IReferenceResolutionInfo resolution) {
		IRegion region = SpanUtils.toRegion(resolution.getReferenceRange());
		return resolution.getDefinitions()
				.stream()
				.map(d -> createHyperlink(region, d))
				.toArray(IHyperlink[]::new);
	}
	
	private IHyperlink createHyperlink(IRegion region, IDefinition definition) {
		ISymbol typeSymbol = this.typeProviderService.getType(definition.getSymbol(), NullCancellationToken.INSTANCE);
		String type = typeSymbol != null ? typeSymbol.getLabel() : null;
		return hyperlinkFactory.create(definition.getSymbol(), type, region);
	}
}
