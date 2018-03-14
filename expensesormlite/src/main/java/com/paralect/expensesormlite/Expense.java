package com.paralect.expensesormlite;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.expense.ExpenseUnit;
import com.paralect.expense.ExpenseUtil;
import com.paralect.expense.ExtendedExpense;

import java.util.Date;

import static com.paralect.core.BaseExpense.EXPENSE_TABLE_NAME;
import static com.paralect.expense.ExpenseUtil.UNITY_CURRENCY;
import static com.paralect.expense.ExpenseUtil.UNITY_KM;
import static com.paralect.expense.ExpenseUtil.UNITY_MIN;
import static com.paralect.expense.ExpenseUnit.Type.MATERIAL;
import static com.paralect.expense.ExpenseUnit.Type.TIME;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

@DatabaseTable(tableName = EXPENSE_TABLE_NAME)
public class Expense implements ExtendedExpense, Parcelable {

    @DatabaseField(columnName = EXPENSE_ID, generatedId = true)
    private long expenseId;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = VALUE)
    private long value;

    @DatabaseField(columnName = UNIT_NAME)
    private String unitName;

    @DatabaseField(columnName = CREATION_DATE)
    private long creationDate;

    @ExpenseUnit.Type
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

    private String valueWithUnit;

    // region Create new expense
    public static Expense copy(Expense ex) {
        Expense expense = new Expense();
        if (ex != null) {
            expense.setName(ex.getName());
            expense.setValue(ex.getValue());
            expense.setUnitName(ex.getUnitName());
            expense.setCreationDate(new Date());
            expense.setType(ex.getType());
            expense.setDiscount(ex.getDiscount());
            expense.setJobId(ex.getJobId());
            ex.setJobId(ex.getJobId());
            ex.setMaterialId(ex.getMaterialId());
            ex.setWorkTypeId(ex.getWorkTypeId());
        }
        return expense;
    }

    public static Expense createTimeExpense(String jobId, String name, int hours, int minutes) {
        long total = hours * 60 + minutes;
        Expense expense = new Expense();
        expense.setType(TIME);
        expense.setName(name);
        expense.setJobId(jobId);
        expense.setCreationDate(new Date());
        expense.setValue(total);
        return expense;
    }

    public static Expense createMaterialExpense(String jobId, String materialName, String materialId, int countOfMaterials) {
        Expense expense = new Expense();
        expense.setJobId(jobId);
        expense.setName(materialName);
        expense.setMaterialId(materialId);
        expense.setType(MATERIAL);
        expense.setCreationDate(new Date());
        expense.setValue(countOfMaterials);
        return expense;
    }
    // endregion

    // region Parcel
    protected Expense(Parcel in) {
        expenseId = in.readLong();
        name = in.readString();
        value = in.readLong();
        unitName = in.readString();
        creationDate = in.readLong();
        type = in.readString();
        discount = in.readFloat();
        jobId = in.readString();
        materialId = in.readString();
        workTypeId = in.readString();

        valueWithUnit = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(expenseId);
        dest.writeString(name);
        dest.writeLong(value);
        dest.writeString(unitName);
        dest.writeLong(creationDate);
        dest.writeString(type);
        dest.writeFloat(discount);
        dest.writeString(jobId);
        dest.writeString(materialId);
        dest.writeString(workTypeId);

        dest.writeString(valueWithUnit);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };
    // endregion

    public Expense() {

    }

    @Override
    public long getId() {
        return expenseId;
    }

    @Override
    public void setId(long id) {
        // no need
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
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public float getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public String getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(String id) {
        jobId = id;
    }

    @Override
    public String getMaterialId() {
        return materialId;
    }

    @Override
    public void setMaterialId(String id) {
        materialId = id;
    }

    @Override
    public boolean isMaterialExpense() {
        return materialId != null;
    }

    @Override
    public String getWorkTypeId() {
        return workTypeId;
    }

    @Override
    public void setWorkTypeId(String id) {
        workTypeId = id;
    }

    @Override
    public String getValueWithUnit() {
        return valueWithUnit;
    }

    @Override
    public void setValueWithUnit(ExpenseUnit expenseUnitCallback) {
        valueWithUnit = ExpenseUtil.getUnit(type, expenseUnitCallback);
    }

    public static class ExpenseValueWithUnit implements ExpenseUnit {

        long value;

        ExpenseValueWithUnit setValue(long value) {
            this.value = value;
            return this;
        }

        @Override
        public String getTimeUnit() {
            return ExpenseUtil.timeToString(value);
        }

        @Override
        public String getDrivingUnit() {
            return value + " " + UNITY_KM;
        }

        @Override
        public String getOtherUnit() {
            return value + " " + UNITY_CURRENCY;
        }

        @Override
        public String getMaterialUnit() {
            return "";
        }
    }

    public static class ExpenseUnitName implements ExpenseUnit {

        @Override
        public String getTimeUnit() {
            return UNITY_MIN;
        }

        @Override
        public String getDrivingUnit() {
            return UNITY_KM;
        }

        @Override
        public String getOtherUnit() {
            return UNITY_CURRENCY;
        }

        @Override
        public String getMaterialUnit() {
            return "";
        }
    }
}
