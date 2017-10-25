package org.slf4j.impl;

import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import java.io.Console;
import java.io.Serializable;

/**
 * Logs to the console view.
 */
public final class ConsoleLoggerAdapter extends LoggerBase implements Serializable {

    /**
     * Initializes a new instance of the {@link LoggerBase} class.
     *
     * @param name The name of the logger.
     */
    public ConsoleLoggerAdapter(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void log(Severity severity, String msg, Throwable t) {
        System.out.println(severity.toString() + ": " + msg);
        if (t != null) {
            System.out.println(".. caused by " + t.getClass().getName());
            t.printStackTrace();
        }
    }
}
