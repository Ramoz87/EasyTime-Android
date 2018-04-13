package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.paralect.datasource.expense.BaseExpense;
import com.paralect.datasource.expense.BaseExpenseImpl;

import java.util.Date;

import static com.example.paralect.easytime.model.Constants.CREATION_DATE;
import static com.example.paralect.easytime.model.Constants.DISCOUNT;
import static com.example.paralect.easytime.model.Constants.EXPENSE_ID;
import static com.example.paralect.easytime.model.Constants.EXPENSE_TABLE_NAME;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.MATERIAL_ID;
import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.TYPE;
import static com.example.paralect.easytime.model.Constants.VALUE;
import static com.example.paralect.easytime.model.Constants.WORK_TYPE_ID;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

@Entity(tableName = EXPENSE_TABLE_NAME)
public class ExpenseEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EXPENSE_ID)
    private long expenseId;

    @ColumnInfo(name = NAME)
    private String name;

    @ColumnInfo(name = VALUE)
    private long value;

    @ColumnInfo(name = CREATION_DATE)
    private long creationDate;

    @ColumnInfo(name = TYPE)
    private String type;

    @ColumnInfo(name = DISCOUNT)
    private float discount;

    // region external Ids
    @ColumnInfo(name = JOB_ID)
    private String jobId;

    @ColumnInfo(name = MATERIAL_ID)
    private String materialId;

    @ColumnInfo(name = WORK_TYPE_ID)
    private String workTypeId;
    // endregion

    // region Getters & Setters
    public ExpenseEntity() {

    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long date) {
        this.creationDate = date;
    }

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
}
