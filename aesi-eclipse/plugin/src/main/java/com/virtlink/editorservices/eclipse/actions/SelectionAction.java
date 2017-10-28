package com.virtlink.editorservices.eclipse.actions;

import javax.annotation.Nullable;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;

import com.virtlink.editorservices.eclipse.Contract;

/**
 * An action that depends on the current selection.
 */
public abstract class SelectionAction extends Action implements ISelectionChangedListener {

	private final IWorkbenchSite site;
	private ISelectionProvider selectionProvider = null;

	/**
	 * Initializes a new instance of this {@link SelectionAction}
	 * with no text and no image.
	 * 
	 * @param site The site this action works on.
	 */
	protected SelectionAction(
			final IWorkbenchSite site
	) { this(site, null, null); }

	/**
	 * Initializes a new instance of this {@link SelectionAction}
	 * with the specified text and no image.
	 * 
	 * @param site The site this action works on.
	 * @param text The action's text; or null.
	 */
	protected SelectionAction(
			final IWorkbenchSite site,
			@Nullable final String text
	) { this(site, text, null); }

	/**
	 * Initializes a new instance of this {@link SelectionAction}
	 * with the specified text and image.
	 * 
	 * @param site The site this action works on.
	 * @param text The action's text; or null.
	 * @param image The action's image; or null.
	 */
	protected SelectionAction(
			final IWorkbenchSite site,
			@Nullable final String text,
			@Nullable final ImageDescriptor image
	) {
		super(text, image);
		assert site != null;
		
		this.site = site;
	}

	/**
	 * Gets the site that owns this action.
	 * 
	 * @return The site.
	 */
	public IWorkbenchSite getSite() {
		return this.site;
	}

	/**
	 * Gets the shell of the site that owns this action.
	 *
	 * @return The site's shell.
	 */
	public Shell getShell() {
		return getSite().getShell();
	}

	/**
	 * Gets the current selection.
	 * 
	 * @return The current selection.
	 */
	@Nullable
	public ISelection getSelection() {
		final ISelectionProvider selectionProvider = getSelectionProvider();
		if (selectionProvider == null) { return null; }
		
		return selectionProvider.getSelection();
	}

	/**
	 * Gets the selection provider.
	 *
	 * @return The selection provider.
	 */
	@Nullable
	public ISelectionProvider getSelectionProvider() {
		if (this.selectionProvider != null) {
			return this.selectionProvider;
		} else {
			return getSite().getSelectionProvider();
		}
	}

	/**
	 * Sets the selection provider.
	 * 
	 * @param provider The new selection provider; or null to use the site's selection provider.
	 */
	public void setSpecialSelectionProvider(@Nullable ISelectionProvider provider) {
		this.selectionProvider = provider;
	}

	/**
	 * Executes this action.
	 * 
	 * The default implementation calls the appropriate overload of {@link doRun}.
	 */
	@Override
	public void run() {
		ISelection selection = getSelection();
		
		if (selection instanceof IStructuredSelection) {
			doRun((IStructuredSelection)selection);
	    } else if (selection instanceof ITextSelection) {
	    	doRun((ITextSelection)selection);
		} else {
			doRun(selection);
		}
	}

	/**
	 * Executes this action with the given selection.
	 * 
	 * The default implementation calls the {@link ISelection} overload
	 * of {@link doRun}.
	 *
	 * @param selection The current selection.
	 */
	protected void doRun(IStructuredSelection selection) {
		doRun((ISelection)selection);
	}

	/**
	 * Executes this action with the given selection.
	 * 
	 * The default implementation calls the {@link ISelection} overload
	 * of {@link doRun}.
	 *
	 * @param selection The current selection.
	 */
	protected void doRun(ITextSelection selection) {
		doRun((ISelection)selection);
	}


	/**
	 * Executes this action with the given selection.
	 * 
	 * The default implementation does nothing.
	 *
	 * @param selection The current selection.
	 */
	protected void doRun(ISelection selection) {
		// Nothing to do.
	}
	
	/**
	 * Occurs when the current selection has changed.
	 * 
	 * The default implementation calls the appropriate overload of {@link onSelectionChanged}.
	 * 
	 * @param event The event.
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		
		if (selection instanceof IStructuredSelection) {
			onSelectionChanged((IStructuredSelection)selection);
		} else if (selection instanceof ITextSelection) {
			onSelectionChanged((ITextSelection)selection);
		} else {
			onSelectionChanged(selection);
		}
	}

	/**
	 * Occurs when the current selection has changed.
	 * 
	 * The default implementation calls the {@link ISelection} overload
	 * of {@link onSelectionChanged}.
	 * 
	 * @param selection The new selection.
 	 */
	protected void onSelectionChanged(IStructuredSelection selection) {
		onSelectionChanged((ISelection)selection);
	}

	/**
	 * Occurs when the current selection has changed.
	 * 
	 * The default implementation calls the {@link ISelection} overload
	 * of {@link onSelectionChanged}.
	 * 
	 * @param selection The new selection.
 	 */
	protected void onSelectionChanged(ITextSelection selection) {
		onSelectionChanged((ISelection)selection);
	}

	/**
	 * Occurs when the current selection has changed.
	 * 
	 * The default implementation does nothing.
	 * 
	 * @param selection The new selection.
 	 */
	protected void onSelectionChanged(ISelection selection) {
		// Nothing to do.
	}
}
