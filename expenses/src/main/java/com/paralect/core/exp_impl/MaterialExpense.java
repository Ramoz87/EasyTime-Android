package com.paralect.core.exp_impl;


import com.paralect.core.Expense;

public abstract class MaterialExpense implements Expense {

    @Override
    public final long getTypeId() {
        return ExpenseIds.MATERIAL;
    }

    public abstract long getMaterialTypeId();
}
