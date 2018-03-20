package com.paralect.expensesormlite;

import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.core.DataSource;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.paralect.expensesormlite.Expense.CREATION_DATE;
import static com.paralect.expensesormlite.Expense.EXPENSE_ID;
import static com.paralect.expensesormlite.Expense.JOB_ID;
import static com.paralect.expensesormlite.Expense.NAME;
import static com.paralect.expensesormlite.Expense.TYPE;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ORMLiteExpenseDataSource extends DataSource<Expense, QueryBuilder<Expense, Long>> {

    private Dao<Expense, Long> dao;

    public ORMLiteExpenseDataSource(Dao<Expense, Long> expenseDao) {
        dao = expenseDao;
    }

    // region Basic methods
    @Override
    public Expense getModel(QueryBuilder<Expense, Long> parameter) throws SQLException {
        return dao.query(parameter.prepare()).get(0);
    }

    @Override
    public Expense getModelById(long id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Expense> getModels(QueryBuilder<Expense, Long> qb) throws SQLException {
        return qb.query();
    }

    @Override
    public void saveModel(Expense expense) throws SQLException {
        dao.createOrUpdate(expense);
    }

    @Override
    public void deleteModel(Expense expense) throws SQLException {
        dao.delete(expense);
    }
    // endregion

    // region Additional methods

    /**
     * Save and retrieve last Expense from the table
     *
     * @param expense that will be saved
     * @return saved Expense
     */
    public Expense saveAndGetExpense(Expense expense) throws SQLException {
        QueryBuilder<Expense, Long> query = dao.queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L);
        saveModel(expense);
        return getModel(query);
    }

    /**
     * Using for query expenses by date and jobId
     *
     * @param jobId is field of Job object
     * @param date  should be in "yyyy-MM-dd" format
     * @return list of expenses
     */
    public List<Expense> getExpenses(String jobId, String date) throws SQLException {
        boolean hasDate = !TextUtils.isEmpty(date);
        QueryBuilder<Expense, Long> qb = dao.queryBuilder();
        Where where = qb.where().eq(JOB_ID, jobId);
        if (hasDate) {

            Date time = ExpenseUtil.dateFromString(date, ExpenseUtil.SHORT_DATE_FORMAT);

            Calendar yesterday = Calendar.getInstance();
            yesterday.setTime(time);

            Calendar tomorrow = Calendar.getInstance();
            tomorrow.setTime(time);
            tomorrow.add(Calendar.DATE, 1);

            long beforeTime = yesterday.getTimeInMillis();
            long afterTime = tomorrow.getTimeInMillis();
            where.and().between(CREATION_DATE, beforeTime, afterTime);

        }
        qb.orderBy(CREATION_DATE, false);
        return getModels(qb);
    }

    /**
     * Using for query expenses by searching name and expense type
     * <p>
     * Doesn't work in case of case sensitive: qb.distinct().selectColumns("name");
     *
     * @param jobId       is field of Job object
     * @param searchQuery for searching in name field
     * @param expenseType
     * @return list of expenses
     */
    public List<Expense> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {
        QueryBuilder<Expense, Long> qb = dao.queryBuilder();

        Where where = qb.where().eq(JOB_ID, jobId);

        if (!TextUtils.isEmpty(searchQuery))
            where.and().like(NAME, "%" + searchQuery + "%");

        if (!TextUtils.isEmpty(expenseType))
            where.and().eq(TYPE, expenseType);

        return getModels(qb);
    }

    /**
     * @param jobId is field of Job object
     * @return total count of expenses for Job object with jobId field
     */
    public long getTotalExpensesCount(String jobId) throws SQLException {
        Where where = dao.queryBuilder().where().eq(JOB_ID, jobId);
        return where.countOf();
    }
    // endregion
}
