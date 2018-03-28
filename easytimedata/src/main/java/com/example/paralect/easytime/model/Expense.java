package com.example.paralect.easytime.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.paralect.easytime.utils.ExpenseUtil;
import com.paralect.easytimemodel.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class Expense implements Parcelable, InvoiceCell {

    private String expenseId;
    private String name;
    private long value;
    private long creationDate;
    @ExpenseUnit.Type
    private String type;
    private float discount;

    // region external Ids
    private String jobId;
    private String materialId;
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
        expenseId = in.readString();
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
        dest.writeString(expenseId);
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

    public String getValueWithUnitName() {
        return valueWithUnitName;
    }

    public void setValueWithUnitName(ExpenseUnit expenseUnitCallback) {
        valueWithUnitName = ExpenseUtil.getUnit(type, expenseUnitCallback);
    }
    // endregion

    // region InvoiceCell
    public String name() {
        return getName();
    }

    public String value() {
        return getValueWithUnitName();
    }

    public int invoiceCellType() {
        return InvoiceCell.Type.CELL;
    }

    public String getId() {
        return expenseId;
    }

    public void setId(String id) {
        expenseId = id;
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
     * @param jobId is field of JobEntity object
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
