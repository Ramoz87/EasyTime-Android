package com.paralect.sqlite;

import com.paralect.expense.ExpenseDataSource;
import com.paralect.expense.ExtendedExpense;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class SQLiteNativeExpenseDataSource extends ExpenseDataSource<ExtendedExpense> {


    @Override
    public void saveModel(ExtendedExpense model) throws SQLException {

    }

    @Override
    public ExtendedExpense saveAndGetModel(ExtendedExpense model) throws SQLException {
        return null;
    }

    @Override
    public long deleteModel(ExtendedExpense model) throws SQLException {
        return 0;
    }

    @Override
    public ExtendedExpense getModelById(long id) throws SQLException {
        return null;
    }

    @Override
    public List<ExtendedExpense> getModels() throws SQLException {
        return null;
    }

    @Override
    protected List<ExtendedExpense> getExpenses(String jobId, String date) throws SQLException {
        return null;
    }

    @Override
    protected List<ExtendedExpense> getExpenses(String jobId, String searchQuery, String expenseType) throws SQLException {
        return null;
    }

    @Override
    protected long getTotalExpensesCount(String jobId) throws SQLException {
        return 0;
    }

}
