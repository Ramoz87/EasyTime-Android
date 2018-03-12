package com.paralect.expences;

import com.paralect.base.DataSource;
import com.paralect.sqlite.Expense;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class ExpenseDataSource<MODEL extends IExpense> extends DataSource<MODEL> {

    public abstract MODEL saveExpense(MODEL expense) throws SQLException;

    public abstract long deleteExpense(MODEL expense) throws SQLException;

    public abstract MODEL getExpenseById(long id) throws SQLException;

    public abstract List<MODEL> getExpensesByType(String type, long typeId);

    public abstract List<MODEL> getExpenses();
    
}
