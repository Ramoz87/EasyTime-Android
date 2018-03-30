package com.paralect.easytimedataretrofit.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.paralect.datasource.core.EntityRequest;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.datasource.retrofit.RetrofitRequest;
import com.paralect.easytimedataretrofit.model.ExpenseEntity;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class ExpenseRequest extends RetrofitRequest<ExpenseEntity, Expense> {

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
    public void queryGet() throws Throwable {
        queryGet("/prilaga/?__a=1");
    }
    // endregion
}
