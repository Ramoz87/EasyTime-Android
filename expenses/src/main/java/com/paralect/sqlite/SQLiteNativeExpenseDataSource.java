package com.paralect.sqlite;

import com.paralect.expense.ExpenseDataSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class SQLiteNativeExpenseDataSource extends ExpenseDataSource<Expense> {

    @Override
    public Expense saveModel(Expense expense) throws SQLException {
        return null;
    }

    @Override
    public long deleteModel(Expense expense) throws SQLException {
        return 0;
    }

    @Override
    public Expense getModelById(long id) throws SQLException {
        return null;
    }

    @Override
    public List<Expense> getModels() throws SQLException {
        return null;
    }
}
