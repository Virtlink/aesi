package com.virtlink.editorservices.eclipse;

import org.eclipse.core.runtime.IProgressMonitor;

import com.virtlink.editorservices.ICancellationToken;

/**
 * Cancellation token based on a progress monitor.
 */
public final class MonitorCancellationToken implements ICancellationToken {

	/** The progress monitor. */
	private final IProgressMonitor monitor;
	
	/**
	 * Initializes a new instance of the {@link MonitorCancellationToken} class.
	 * 
	 * @param monitor The progress monitor.
	 */
	public MonitorCancellationToken(final IProgressMonitor monitor) {
		Contract.requireNotNull("monitor", monitor);
		
		this.monitor = monitor;
	}
	
	@Override
	public boolean getCancelled() {
		return this.monitor.isCanceled();
	}

}
