package com.paralect.expense;

import com.paralect.core.DataSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class ExpenseDataSource<EXPENSE extends ExtendedExpense> extends DataSource<EXPENSE> {

    /**
     * Using for query expenses by date and jobId
     *
     * @param jobId is field of Job object
     * @param date  should be in "yyyy-MM-dd" format
     * @return list of expenses
     */
    protected abstract List<EXPENSE> getExpenses(String jobId, String date) throws SQLException;

    /**
     * Using for query expenses by searching name and expense type
     *
     * @param jobId       is field of Job object
     * @param searchQuery for searching in name field
     * @param expenseType
     * @return list of expenses
     */
    protected abstract List<EXPENSE> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException;

    /**
     * @param jobId is field of Job object
     * @return total count of expenses for Job object with jobId field
     */
    protected abstract long getTotalExpensesCount(String jobId) throws SQLException;

}
