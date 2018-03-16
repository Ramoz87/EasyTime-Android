package com.paralect.expensesormlite;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
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

public class ORMLiteExpenseDataSource extends DataSource<Expense> {

    private Context context;
    private Dao<Expense, Long> dao;


    public ORMLiteExpenseDataSource(Context context, Dao<Expense, Long> expenseDao) {
        this.context = context;
        dao = expenseDao;
    }

    // region Basic methods
    @Override
    public void saveModel(Expense expense) throws SQLException {
        dao.createOrUpdate(expense);
    }

    @Override
    public Expense saveAndGetModel(Expense expense) throws SQLException {
        // save
        saveModel(expense);
        // retrieve
        PreparedQuery<Expense> query = dao.queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L)
                .prepare();
        Expense ex = dao.query(query).get(0);
        return ex;
    }

    @Override
    public long deleteModel(Expense expense) throws SQLException {
        long id = expense.getId();
        dao.delete(expense);
        return id;
    }

    @Override
    public Expense getModelById(long id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Expense> getModels() {
        return null;
    }
    // endregion

    // region Additional methods

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
        List<Expense> expenses = qb.query();
        return expenses;
    }

    /**
     * Using for query expenses by searching name and expense type
     *
     * @param jobId       is field of Job object
     * @param searchQuery for searching in name field
     * @param expenseType
     * @return list of expenses
     */
    public List<Expense> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {
        QueryBuilder<Expense, Long> qb = dao.queryBuilder();

//            // Doesn't work in case of case sensitive
//            qb.distinct().selectColumns("name");

        Where where = qb.where().eq(JOB_ID, jobId);

        if (!TextUtils.isEmpty(searchQuery))
            where.and().like(NAME, "%" + searchQuery + "%");

        if (!TextUtils.isEmpty(expenseType))
            where.and().eq(TYPE, expenseType);
//        String query = qb.prepareStatementString();
        List<Expense> expenses = qb.query();
        return expenses;
    }

    /**
     * @param jobId is field of Job object
     * @return total count of expenses for Job object with jobId field
     */
    public long getTotalExpensesCount(String jobId) throws SQLException {
        Where where = dao.queryBuilder().where().eq(JOB_ID, jobId);
        long totalCount = where.countOf();
        return totalCount;
    }
    // endregion
}
