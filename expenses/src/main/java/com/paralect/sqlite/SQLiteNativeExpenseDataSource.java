package com.paralect.sqlite;

import com.paralect.expences.ExpenseDataSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class SQLiteNativeExpenseDataSource extends ExpenseDataSource<Expense> {


    @Override
    public Expense saveExpense(Expense expense) throws SQLException {
        return null;
    }

    @Override
    public void deleteExpense(Expense expense) {

    }

    @Override
    public Expense getExpenseById(long id) {
        return null;
    }

    @Override
    public List<Expense> getExpensesByType(String type, long typeId) {
        return null;
    }

    @Override
    public List<Expense> getExpenses() {
        return null;
    }
}
