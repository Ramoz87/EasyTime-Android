package com.paralect.core;


public interface Evaluator {
    /**
     * evaluates some expenses and calculates cost
     * @param typeId type of expense
     * @param value count of expense
     * @return cost of expense
     */
    double evaluate(long typeId, double value);
}
