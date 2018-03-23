package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.datasource.expense.BaseExpense;

import java.util.Date;

import static com.paralect.easytimedataormlite.model.Expense.EXPENSE_TABLE_NAME;


/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

@DatabaseTable(tableName = EXPENSE_TABLE_NAME)
public class Expense implements BaseExpense<Long> {

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

    @DatabaseField(columnName = EXPENSE_ID, generatedId = true)
    private long expenseId;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = VALUE)
    private long value;

    @DatabaseField(columnName = CREATION_DATE)
    private long creationDate;

    @DatabaseField(columnName = TYPE)
    private String type;

    @DatabaseField(columnName = DISCOUNT, dataType = DataType.FLOAT)
    private float discount;

    // region external Ids
    @DatabaseField(columnName = JOB_ID)
    private String jobId;

    @DatabaseField(columnName = MATERIAL_ID)
    private String materialId;

    @DatabaseField(columnName = WORK_TYPE_ID)
    private String workTypeId;
    // endregion

    // region Getters & Setters
    public Expense() {

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

    @Override
    public Long getId() {
        return expenseId;
    }

    @Override
    public void setId(Long aLong) {

    }
}
