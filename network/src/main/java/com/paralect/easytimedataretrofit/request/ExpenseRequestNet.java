package com.paralect.easytimedataretrofit.request;

import com.example.paralect.easytime.model.Expense;
import com.paralect.datasource.network.NetworkRequest;
import com.paralect.easytimedataretrofit.model.ExpenseEntity;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class ExpenseRequestNet<P> extends NetworkRequest<ExpenseEntity, Expense, P> {

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

    // region Requests
  
    // endregion
}
