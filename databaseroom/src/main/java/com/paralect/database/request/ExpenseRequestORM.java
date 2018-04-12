package com.paralect.database.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.paralect.database.model.ExpenseEntity;
import com.paralect.datasource.database.QueryBuilder;
import com.paralect.datasource.room.RoomRequest;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static com.example.paralect.easytime.model.Constants.CREATION_DATE;
import static com.example.paralect.easytime.model.Constants.EXPENSE_ID;
import static com.example.paralect.easytime.model.Constants.EXPENSE_TABLE_NAME;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.TYPE;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class ExpenseRequestORM extends RoomRequest<ExpenseEntity, Expense> {

    @Override
    public Expense toAppEntity(ExpenseEntity ex) {
        if (ex == null) return null;
        Expense in = new Expense();
        in.setExpenseId(ex.getExpenseId());
        in.setName(ex.getName());
        in.setValue(ex.getValue());
        in.setCreationDate(ex.getCreationDate());
        in.setType(ex.getType());
        in.setDiscount(ex.getDiscount());
        in.setJobId(ex.getJobId());
        in.setMaterialId(ex.getMaterialId());
        in.setWorkTypeId(ex.getWorkTypeId());
        return in;
    }

    @Override
    public ExpenseEntity toDataSourceEntity(Expense in) {
        if (in == null) return null;
        ExpenseEntity ex = new ExpenseEntity();
        ex.setExpenseId(in.getExpenseId());
        ex.setName(in.getName());
        ex.setValue(in.getValue());
        ex.setCreationDate(in.getCreationDate());
        ex.setType(in.getType());
        ex.setDiscount(in.getDiscount());
        ex.setJobId(in.getJobId());
        ex.setMaterialId(in.getMaterialId());
        ex.setWorkTypeId(in.getWorkTypeId());
        return ex;
    }

    @Override
    public Class<ExpenseEntity> getDataSourceEntityClazz() {
        return ExpenseEntity.class;
    }

    @Override
    public Class<Expense> getAppEntityClazz() {
        return Expense.class;
    }

    @Override
    public String getTableName() {
        return EXPENSE_TABLE_NAME;
    }

    // region Requests
    public void queryForLast() throws Exception {
        queryForLast(EXPENSE_ID);
    }

    /**
     * Using for query expenses by searching name and expense type
     * <p>
     * Doesn't work in case of case sensitive: qb.distinct().selectColumns("name");
     *
     * @param jobId       is field of JobEntity object
     * @param searchQuery for searching in name field
     * @param expenseType
     * @return list of expenses
     */
    public void queryForListExpense(final String jobId, final String searchQuery, @ExpenseUnit.Type final String expenseType) throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .select()
                .from(getTableName())
                .where(JOB_ID)
                .eq(jobId);

        if (!TextUtils.isEmpty(searchQuery))
            queryBuilder.and(NAME).like("%" + searchQuery + "%");

        if (!TextUtils.isEmpty(searchQuery))
            queryBuilder.and(TYPE).eq(expenseType);
        setQueryBuilder(queryBuilder);
    }

    public void queryForListExpense(final String jobId, final String date) throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .select()
                .from(getTableName())
                .where(JOB_ID)
                .eq(jobId);

        boolean hasDate = !TextUtils.isEmpty(date);
        if (hasDate) {

            Date time = ExpenseUtil.dateFromString(date, SHORT_DATE_FORMAT);

            Calendar yesterday = Calendar.getInstance();
            yesterday.setTime(time);

            Calendar tomorrow = Calendar.getInstance();
            tomorrow.setTime(time);
            tomorrow.add(Calendar.DATE, 1);

            long beforeTime = yesterday.getTimeInMillis();
            long afterTime = tomorrow.getTimeInMillis();
            queryBuilder
                    .and(CREATION_DATE).greater(beforeTime)
                    .and(CREATION_DATE).less(afterTime);
        }
        setQueryBuilder(queryBuilder);
    }

    public void queryCountOfJobs(final String jobId) throws Exception {
        queryForCount(JOB_ID, jobId);
    }
    // endregion
}
