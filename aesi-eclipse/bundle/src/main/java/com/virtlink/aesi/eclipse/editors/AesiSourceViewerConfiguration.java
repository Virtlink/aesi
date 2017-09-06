package com.virtlink.aesi.eclipse.editors;

import com.google.inject.Inject;
import com.virtlink.aesi.eclipse.AesiPlugin;
import com.virtlink.aesi.eclipse.codecompletion.AesiCompletionProcessor;
import com.virtlink.aesi.eclipse.syntaxcoloring.AsyncPresentationReconciler;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

/**
 * The AESI source viewer configuration.
 */
public class AesiSourceViewerConfiguration extends TextSourceViewerConfiguration {
	private XMLDoubleClickStrategy doubleClickStrategy;
//	private XMLTagScanner tagScanner;
//	private XMLScanner scanner;
	private AesiSourceScanner scanner;
	private final ColorManager colorManager;
	private final IContentAssistProcessorFactory contentAssistProcessorFactory;

	@Inject
	public AesiSourceViewerConfiguration(ColorManager colorManager, IContentAssistProcessorFactory contentAssistProcessorFactory) {
		this.colorManager = colorManager;
		this.contentAssistProcessorFactory = contentAssistProcessorFactory;
	}

//	@Override
//	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
//		return new String[] {
//			IDocument.DEFAULT_CONTENT_TYPE,
//			XMLPartitionScanner.XML_COMMENT,
//			XMLPartitionScanner.XML_TAG };
//	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new XMLDoubleClickStrategy();
		return doubleClickStrategy;
	}
	
	protected AesiSourceScanner getScanner() {
		if (scanner == null) {
			scanner = new AesiSourceScanner();
		}
		return scanner;
	}

//	protected XMLScanner getXMLScanner() {
//		if (scanner == null) {
//			scanner = new XMLScanner(colorManager);
//			scanner.setDefaultReturnToken(
//				new Token(
//					new TextAttribute(
//						colorManager.getColor(IXMLColorConstants.DEFAULT))));
//		}
//		return scanner;
//	}
//	protected XMLTagScanner getXMLTagScanner() {
//		if (tagScanner == null) {
//			tagScanner = new XMLTagScanner(colorManager);
//			tagScanner.setDefaultReturnToken(
//				new Token(
//					new TextAttribute(
//						colorManager.getColor(IXMLColorConstants.TAG))));
//		}
//		return tagScanner;
//	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
//		AsyncPresentationReconciler reconciler = new AsyncPresentationReconciler();
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getScanner());
//		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

//		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getXMLTagScanner());
//		reconciler.setDamager(dr, XMLPartitionScanner.XML_TAG);
//		reconciler.setRepairer(dr, XMLPartitionScanner.XML_TAG);
//
//		dr = new DefaultDamagerRepairer(getXMLScanner());
//		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
//		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
//
//		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
//				new TextAttribute(colorManager.getColor(IXMLColorConstants.XML_COMMENT)));
//		reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
//		reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

		return reconciler;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant ca = new ContentAssistant();
//		AesiPlugin.getInjector()
		IContentAssistProcessor cap = this.contentAssistProcessorFactory.create();
		ca.setContentAssistProcessor(cap, IDocument.DEFAULT_CONTENT_TYPE);
		ca.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		return ca;
	}
}