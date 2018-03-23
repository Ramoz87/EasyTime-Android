package com.example.paralect.easytime.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.paralect.easytime.R;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.paralect.datasource.expense.BaseExpense;
import com.example.paralect.easytime.utils.ExpenseUtil;
import com.example.paralect.easytime.main.projects.project.invoice.InvoiceCell;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.paralect.easytime.model.Expense.EXPENSE_TABLE_NAME;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.DRIVING;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.OTHER;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;
import static com.example.paralect.easytime.model.ExpenseUnit.Type.TIME;
import static com.example.paralect.easytime.utils.ExpenseUtil.UNITY_CURRENCY;
import static com.example.paralect.easytime.utils.ExpenseUtil.UNITY_KM;
import static com.example.paralect.easytime.utils.ExpenseUtil.UNITY_MIN;


/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

@DatabaseTable(tableName = EXPENSE_TABLE_NAME)
public class Expense implements BaseExpense<Long>, Parcelable, InvoiceCell {

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

    private String valueWithUnitName;

    // region Create new expense
    public static Expense copy(Expense ex) {
        Expense expense = new Expense();
        if (ex != null) {
            expense.setName(ex.getName());
            expense.setValue(ex.getValue());
            expense.setCreationDate(new Date());
            expense.setType(ex.getType());
            expense.setDiscount(ex.getDiscount());
            expense.setJobId(ex.getJobId());
            expense.setMaterialId(ex.getMaterialId());
            expense.setWorkTypeId(ex.getWorkTypeId());
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
        creationDate = in.readLong();
        type = in.readString();
        discount = in.readFloat();
        jobId = in.readString();
        materialId = in.readString();
        workTypeId = in.readString();

        valueWithUnitName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(expenseId);
        dest.writeString(name);
        dest.writeLong(value);
        dest.writeLong(creationDate);
        dest.writeString(type);
        dest.writeFloat(discount);
        dest.writeString(jobId);
        dest.writeString(materialId);
        dest.writeString(workTypeId);

        dest.writeString(valueWithUnitName);
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

    public String getValueWithUnitName() {
        return valueWithUnitName;
    }

    public void setValueWithUnitName(ExpenseUnit expenseUnitCallback) {
        valueWithUnitName = ExpenseUtil.getUnit(type, expenseUnitCallback);
    }
    // endregion

    // region InvoiceCell
    @Override
    public String name() {
        return getName();
    }

    @Override
    public String value() {
        return getValueWithUnitName();
    }

    @Override
    public int invoiceCellType() {
        return InvoiceCell.Type.CELL;
    }

    @Override
    public Long getId() {
        return expenseId;
    }

    @Override
    public void setId(Long aLong) {

    }
    // endregion

    // region Additional classes and methods

    /**
     * Returns value and unit
     */
    public static class ExpenseValueWithUnit implements ExpenseUnit {

        private long value;

        public ExpenseValueWithUnit() {
        }

        public ExpenseValueWithUnit(long value) {
            this.value = value;
        }

        public ExpenseValueWithUnit setValue(long value) {
            this.value = value;
            return this;
        }

        public long getValue() {
            return value;
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

    /**
     * Returns only value unit
     */
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

    /**
     * Expenses which always exists
     *
     * @param jobId is field of Job object
     * @return list of expenses
     */
    public static List<Expense> getDefaultExpenses(Context context, String jobId) {
        List<Expense> expenses = new ArrayList<>();

        // Driving
        Expense expense = new Expense();
        expense.setName(context.getString(R.string.driving));
        expense.setType(DRIVING);
        expense.setJobId(jobId);
        expenses.add(expense);

        // Other expenses
        expense = new Expense();
        expense.setName(context.getString(R.string.other_expenses));
        expense.setType(OTHER);
        expense.setJobId(jobId);
        expenses.add(expense);

        return expenses;
    }
    // endregion
}
