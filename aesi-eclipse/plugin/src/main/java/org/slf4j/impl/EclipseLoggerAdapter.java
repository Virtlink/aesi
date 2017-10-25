package org.slf4j.impl;

//import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import java.io.Serializable;

/**
 * Logs to the Eclipse Error view.
 */
public final class EclipseLoggerAdapter extends LoggerBase implements Serializable {

	private static final long serialVersionUID = -873589042375351L;

	/**
     * Initializes a new instance of the {@link LoggerBase} class.
     *
     * @param name The name of the logger.
     */
    public EclipseLoggerAdapter(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void log(Severity severity, String msg, Throwable t) {
    	// TODO
//        IStatus status = new Status(severity.toStatusInt(), AesiPlugin.PLUGIN_ID, msg, t);
//        AesiPlugin.getDefault().getLog().log(status);
    }
}
