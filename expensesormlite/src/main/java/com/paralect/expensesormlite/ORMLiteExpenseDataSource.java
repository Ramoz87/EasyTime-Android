package com.paralect.expensesormlite;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.expense.ExpenseDataSource;
import com.paralect.expense.ExpenseUnit;
import com.paralect.expense.ExpenseUtil;
import com.prilaga.expensesormlite.R;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.paralect.core.BaseExpense.CREATION_DATE;
import static com.paralect.core.BaseExpense.EXPENSE_ID;
import static com.paralect.core.BaseExpense.NAME;
import static com.paralect.core.BaseExpense.TYPE;
import static com.paralect.expense.ExpenseUnit.Type.DRIVING;
import static com.paralect.expense.ExpenseUnit.Type.OTHER;
import static com.paralect.expense.ExtendedExpense.JOB_ID;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ORMLiteExpenseDataSource extends ExpenseDataSource<Expense> {

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
        return dao.query(query).get(0);
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
        List<Expense> expenses = qb.orderBy(CREATION_DATE, false).query();
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

        Where where = qb.where().eq(JOB_ID, jobId).and();

        if (!TextUtils.isEmpty(searchQuery))
            where.and().like(NAME, "%" + searchQuery + "%");

        if (!TextUtils.isEmpty(expenseType))
            where.and().eq(TYPE, expenseType);

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

    /**
     * Expenses which always exists
     *
     * @param jobId is field of Job object
     * @return list of expenses
     */
    public List<Expense> getDefaultExpenses(String jobId) {
        List<Expense> expenses = new ArrayList<>();

        // Driving
        Expense expense = new Expense();
        expense.setName(context.getString(R.string.driving));
        expense.setType(DRIVING);
        expense.setJobId(jobId);
        expenses.add(expense);

        // Other expenses
        expense = new Expense();
        expense.setName(context.getString(R.string.other_expenses));
        expense.setType(OTHER);
        expense.setJobId(jobId);
        expenses.add(expense);

        return expenses;
    }
    // endregion
}
