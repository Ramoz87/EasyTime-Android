package com.prilaga.expensesormlite;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.paralect.expences.IExpenseDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.paralect.expences.IExpense.EXPENSE_ID;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ORMLiteExpenseDataSource extends IExpenseDataSource<Expense> {

    private Context context;
    private Dao<Expense, Long> dao;

    public ORMLiteExpenseDataSource(Context context, Dao<Expense, Long> expenseDao) {
        this.context = context;
        dao = expenseDao;
    }

    @Override
    public Expense saveExpense(Expense expense) throws SQLException {
        // save
        dao.createOrUpdate(expense);
        // retrieve
        PreparedQuery<Expense> query = dao.queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L)
                .prepare();

        return dao.query(query).get(0);
    }

    @Override
    public long deleteExpense(Expense expense) throws SQLException{
        long id = expense.getId();
        dao.delete(expense);
        return id;
    }

    @Override
    public Expense getExpenseById(long id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Expense> getExpensesByType(String type, long typeId) {
        return null;
    }

    @Override
    public List<Expense> getExpenses() {
        return null;
    }

    // region Additional methods
    public List<Expense> getDefaultExpenses(String jobId) {
        List<Expense> expenses = new ArrayList<>();

//        // Driving
//        Expense expense = new Expense();
//        expense.setName(EasyTimeApplication.getContext().getString(R.string.driving));
//        expense.setType(Expense.Type.DRIVING);
//        expense.setJobId(jobId);
//        expenses.add(expense);
//
//        // Other expenses
//        expense = new Expense();
//        expense.setName(EasyTimeApplication.getContext().getString(R.string.other_expenses));
//        expense.setType(Expense.Type.OTHER);
//        expense.setJobId(jobId);
//        expenses.add(expense);

        return expenses;
    }

    // endregion
}
