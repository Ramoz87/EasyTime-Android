package com.paralect.easytimedataormlite.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.datasource.core.EntityRequest;
import com.paralect.easytimedataormlite.model.ExpenseEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class ExpenseRequest implements EntityRequest<Expense, ExpenseEntity, QueryBuilder<ExpenseEntity, ?>> {

    // region Fields constants
    public static final String EXPENSE_TABLE_NAME = "expenses";
    public static final String EXPENSE_ID = "expenseId";
    public static final String NAME = "name";
    public static final String DISCOUNT = "discount";
    public static final String VALUE = "value";
    public static final String UNIT_NAME = "unitName";
    public static final String CREATION_DATE = "creationDate";
    public static final String TYPE = "type";
    public static final String JOB_ID = "jobId";
    public static final String MATERIAL_ID = "materialId";
    public static final String WORK_TYPE_ID = "workTypeId";
    // endregion

    private QueryBuilder<ExpenseEntity, ?> parameter = null;

    @Override
    public Expense wrap(ExpenseEntity ex) {
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
    public ExpenseEntity unwrap(Expense in) {
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
    public QueryBuilder<ExpenseEntity, ?> getParameter() {
        return parameter;
    }

    @Override
    public Class<Expense> getInnerClazz() {
        return Expense.class;
    }

    @Override
    public Class<ExpenseEntity> getExternalClazz() {
        return ExpenseEntity.class;
    }

    // region Requests
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
    public ExpenseRequest setup(OrmLiteSqliteOpenHelper helper, String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {

        Dao<ExpenseEntity, ?> dao = helper.getDao(ExpenseEntity.class);
        parameter = dao.queryBuilder();

        Where where = parameter.where().eq(JOB_ID, jobId);

        if (!TextUtils.isEmpty(searchQuery))
            where.and().like(NAME, "%" + searchQuery + "%");

        if (!TextUtils.isEmpty(expenseType))
            where.and().eq(TYPE, expenseType);

        return this;
    }

    public ExpenseRequest getLast(OrmLiteSqliteOpenHelper helper)throws SQLException{
        Dao<ExpenseEntity, ?> dao = helper.getDao(ExpenseEntity.class);
        parameter = dao.queryBuilder()
                .orderBy(EXPENSE_ID, false)
                .limit(1L);
        return this;
    }
    // endregion
}
