package com.paralect.easytimedataretrofit.model;

import com.google.gson.annotations.SerializedName;
import com.paralect.datasource.expense.BaseExpenseImpl;

import java.util.Date;

import static com.example.paralect.easytime.model.Constants.CREATION_DATE;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.MATERIAL_ID;
import static com.example.paralect.easytime.model.Constants.WORK_TYPE_ID;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ExpenseEntity extends BaseExpenseImpl {

    private long expenseId;

    @SerializedName(CREATION_DATE)
    private long creationDate;
    private String type;
    private float discount;

    // region external Ids
    @SerializedName(JOB_ID)
    private String jobId;

    @SerializedName(MATERIAL_ID)
    private String materialId;

    @SerializedName(WORK_TYPE_ID)
    private String workTypeId;
    // endregion

    // region Getters & Setters
    public ExpenseEntity() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public long getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(long date) {
        this.creationDate = date;
    }

    @Override
    public void setCreationDate(Date date) {
        if (date != null)
            creationDate = date.getTime();
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String id) {
        jobId = id;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String id) {
        materialId = id;
    }

    public boolean isMaterialExpense() {
        return materialId != null;
    }

    public String getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(String id) {
        workTypeId = id;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }
}
