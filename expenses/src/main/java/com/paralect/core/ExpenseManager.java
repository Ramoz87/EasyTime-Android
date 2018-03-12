package com.paralect.core;


public final class ExpenseManager {
    private ExpenseManager() {
        throw new RuntimeException("no instances");
    }

    public static double totalExpenseCount(Evaluator evaluator, Expense... expenses) {
        double total = 0;
        for (Expense exp : expenses) {
            total += evaluator.evaluate(exp.getTypeId(), exp.count());
        }
        return total;
    }

    private static Expense minExpense(Evaluator evaluator, Expense... expenses) {
        return minMaxExpense(evaluator, false, expenses);
    }

    public static Expense maxExpense(Evaluator evaluator, Expense... expenses) {
        return minMaxExpense(evaluator, true, expenses);
    }

    private static Expense minMaxExpense(Evaluator evaluator, boolean max, Expense... expenses) {
        int length = expenses.length;
        if (length == 0) throw new RuntimeException("no expenses?");
        int index = 0;
        Expense first = expenses[index];
        double val = evaluator.evaluate(first.getTypeId(), first.count());
        for (int i = 1; i < length; i++) {
            double count = evaluator.evaluate(first.getTypeId(), first.count());
            if ((max && count > val) || (!max && count < val)) {
                index = i;
                val = count;
            }
        }
        return expenses[index];
    }

    // etc...
}
