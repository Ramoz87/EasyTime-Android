package com.paralect.easytimedataormlite.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.easytimedataormlite.model.ExpenseEntity;

import java.sql.SQLException;

import static com.paralect.easytimedataormlite.model.ExpenseEntity.EXPENSE_ID;
import static com.paralect.easytimedataormlite.model.ExpenseEntity.JOB_ID;
import static com.paralect.easytimedataormlite.model.ExpenseEntity.NAME;
import static com.paralect.easytimedataormlite.model.ExpenseEntity.TYPE;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class ExpenseRequest extends BaseRequest<Expense, ExpenseEntity, QueryBuilder<ExpenseEntity, ?>> {

    @Override
    public Expense toInner(ExpenseEntity ex) {
        Expense in = null;
        if (ex != null) {
            in = new Expense();
            in.setId(ex.getId());
            in.setName(ex.getName());
            in.setValue(ex.getValue());
            in.setCreationDate(ex.getCreationDate());
            in.setType(ex.getType());
            in.setDiscount(ex.getDiscount());
            in.setJobId(ex.getJobId());
            in.setMaterialId(ex.getMaterialId());
            in.setWorkTypeId(ex.getWorkTypeId());
        }
        return in;
    }

    @Override
    public ExpenseEntity toExternal(Expense in) {
        ExpenseEntity ex = null;
        if (in != null) {
            ex = new ExpenseEntity();
            ex.setId(in.getId());
            ex.setName(in.getName());
            ex.setValue(in.getValue());
            ex.setCreationDate(in.getCreationDate());
            ex.setType(in.getType());
            ex.setDiscount(in.getDiscount());
            ex.setJobId(in.getJobId());
            ex.setMaterialId(in.getMaterialId());
            ex.setWorkTypeId(in.getWorkTypeId());
        }
        return ex;
    }

    @Override
    public Class<Expense> getInnerEntityClazz() {
        return Expense.class;
    }

    @Override
    public Class<ExpenseEntity> getExternalEntityClazz() {
        return ExpenseEntity.class;
    }

    // region Requests
    public ExpenseRequest getExpenseRequest(OrmLiteSqliteOpenHelper helper) throws SQLException {
        Dao<ExpenseEntity, ?> dao = helper.getDao(ExpenseEntity.class);
        QueryBuilder<ExpenseEntity, ?> query = dao.queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L);
        setParameter(query);
        return this;
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
    public ExpenseRequest listExpenseRequest(OrmLiteSqliteOpenHelper helper, String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {

        Dao<ExpenseEntity, ?> dao = helper.getDao(ExpenseEntity.class);
        QueryBuilder<ExpenseEntity, ?> parameter = dao.queryBuilder();

        Where where = parameter.where().eq(JOB_ID, jobId);

        if (!TextUtils.isEmpty(searchQuery))
            where.and().like(NAME, "%" + searchQuery + "%");

        if (!TextUtils.isEmpty(expenseType))
            where.and().eq(TYPE, expenseType);

        setParameter(parameter);
        return this;
    }

    public ExpenseRequest lastExpenseRequest(OrmLiteSqliteOpenHelper helper) throws SQLException {
        Dao<ExpenseEntity, ?> dao = helper.getDao(ExpenseEntity.class);
        QueryBuilder<ExpenseEntity, ?> parameter = dao.queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L);
        setParameter(parameter);
        return this;
    }
    // endregion
}
