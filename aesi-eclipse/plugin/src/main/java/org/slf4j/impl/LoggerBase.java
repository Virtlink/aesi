package org.slf4j.impl;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * Base implementation for loggers.
 */
public abstract class LoggerBase extends MarkerIgnoringBase {

	private static final long serialVersionUID = -682503798840091493L;
	
	private final String name;

    /**
     * Initializes a new instance of the {@link LoggerBase} class.
     * @param name The name of the logger.
     */
    protected LoggerBase(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() { return this.name; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled() { return true; }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() { return true; }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() { return true; }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() { return true; }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() { return true; }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void trace(String msg) {
        if (!this.isTraceEnabled())
            return;
        trace(msg, (Throwable)null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void trace(String format, Object arg) {
        if (!this.isTraceEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg);
        trace(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void trace(String format, Object arg1, Object arg2) {
        if (!this.isTraceEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg1, arg2);
        trace(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void trace(String format, Object... arguments) {
        if (!this.isTraceEnabled())
            return;
        FormattingTuple result = MessageFormatter.arrayFormat(format, arguments);
        trace(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void trace(String msg, Throwable t) {
        if (!this.isTraceEnabled())
            return;
        log(Severity.TRACE, msg, t);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final void debug(String msg) {
        if (!this.isDebugEnabled())
            return;
        debug(msg, (Throwable)null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void debug(String format, Object arg) {
        if (!this.isDebugEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg);
        debug(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void debug(String format, Object arg1, Object arg2) {
        if (!this.isDebugEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg1, arg2);
        debug(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void debug(String format, Object... arguments) {
        if (!this.isDebugEnabled())
            return;
        FormattingTuple result = MessageFormatter.arrayFormat(format, arguments);
        debug(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void debug(String msg, Throwable t) {
        if (!this.isDebugEnabled())
            return;
        log(Severity.DEBUG, msg, t);
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public final void info(String msg) {
        if (!this.isInfoEnabled())
            return;
        info(msg, (Throwable)null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void info(String format, Object arg) {
        if (!this.isInfoEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg);
        info(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void info(String format, Object arg1, Object arg2) {
        if (!this.isInfoEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg1, arg2);
        info(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void info(String format, Object... arguments) {
        if (!this.isInfoEnabled())
            return;
        FormattingTuple result = MessageFormatter.arrayFormat(format, arguments);
        info(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void info(String msg, Throwable t) {
        if (!this.isInfoEnabled())
            return;
        log(Severity.INFO, msg, t);
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public final void warn(String msg) {
        if (!this.isWarnEnabled())
            return;
        warn(msg, (Throwable)null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void warn(String format, Object arg) {
        if (!this.isWarnEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg);
        warn(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void warn(String format, Object arg1, Object arg2) {
        if (!this.isWarnEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg1, arg2);
        warn(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void warn(String format, Object... arguments) {
        if (!this.isWarnEnabled())
            return;
        FormattingTuple result = MessageFormatter.arrayFormat(format, arguments);
        warn(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void warn(String msg, Throwable t) {
        if (!this.isWarnEnabled())
            return;
        log(Severity.WARN, msg, t);
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String msg) {
        if (!this.isErrorEnabled())
            return;
        error(msg, (Throwable)null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String format, Object arg) {
        if (!this.isErrorEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg);
        error(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String format, Object arg1, Object arg2) {
        if (!this.isErrorEnabled())
            return;
        FormattingTuple result = MessageFormatter.format(format, arg1, arg2);
        error(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String format, Object... arguments) {
        if (!this.isErrorEnabled())
            return;
        FormattingTuple result = MessageFormatter.arrayFormat(format, arguments);
        error(result.getMessage(), result.getThrowable());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String msg, Throwable t) {
        if (!this.isErrorEnabled())
            return;
        log(Severity.ERROR, msg, t);
    }


    /**
     * Logs the specified message.
     * @param severity The severity.
     * @param msg The message.
     * @param t Thr throwable; or null.
     */
    protected abstract void log(Severity severity, String msg, Throwable t);

}
