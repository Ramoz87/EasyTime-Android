package com.paralect.core.exp_impl;


import com.paralect.core.Expense;

public abstract class Activity implements Expense {

    @Override
    public final long getTypeId() {
        return ExpenseIds.ACTIVITY;
    }

    public abstract long getTimestampStart();
    public abstract long getTimestampEnd();
}
