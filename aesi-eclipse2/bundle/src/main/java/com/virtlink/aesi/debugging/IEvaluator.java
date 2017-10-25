package com.virtlink.aesi.debugging;

import javax.annotation.Nullable;

/**
 * Evaluates expressions.
 */
public interface IEvaluator {

    /**
     * Evaluates the specified expression.
     *
     * @param expression The expression to evaluate.
     * @param stackFrame The stack frame that provides the scope for the expression;
     *                   or {@code null} to evaluate the expression in the global scope.
     * @return The result of evaluating the expression.
     */
    IValue evaluate(String expression, @Nullable IAesiStackFrame stackFrame);

}
