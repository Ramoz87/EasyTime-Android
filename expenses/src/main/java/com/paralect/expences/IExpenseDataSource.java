package com.paralect.expences;

import com.paralect.base.DataSource;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class IExpenseDataSource<MODEL extends IExpense> extends DataSource<MODEL> {

    public abstract void saveExpense();

    public abstract void deleteExpense();

    public abstract MODEL getExpenseById(long id);

    public abstract List<MODEL> getExpensesByType(String type, long typeId);

    public abstract List<MODEL> getExpenses();
    
}
