package com.paralect.core;


import com.paralect.base.Model;

public interface Expense extends Model, Typed, Countable, Describable {
    @Override double count();
}
