package com.prilaga.expensesormlite;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.paralect.expences.IExpense;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class Expense implements IExpense, Parcelable {

    @StringDef({Type.TIME, Type.MATERIAL, Type.DRIVING, Type.OTHER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Type {
        String TIME = "Time";
        String MATERIAL = "Material";
        String DRIVING = "Driving";
        String OTHER = "Other";
    }

    @DatabaseField(columnName = "expenseId", generatedId = true)
    private long expenseId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "discount", dataType = DataType.FLOAT)
    private float discount;

    @DatabaseField(columnName = "value")
    private long value;

    @DatabaseField(columnName = "unitName")
    private String unitName;

    @DatabaseField(columnName = "creationDate")
    private long creationDate;

    @Type
    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "typeId")
    private long typeId;

    @DatabaseField(columnName = "mainId")
    private String mainId;

    private String typedValue;

    // region Create new expense
    public static Expense create(Expense ex) {
        Expense expense = new Expense();
        if (ex != null) {
            expense.setName(ex.getName());
            expense.setValue(ex.getValue());
            expense.setUnitName(ex.getUnitName());
            expense.setCreationDate(new Date());
            expense.setType(ex.getType());
            expense.setTypeId(ex.getTypeId());
            expense.setMainId(ex.getMainId());
            expense.setDiscount(ex.getDiscount());
        }
        return expense;
    }

    public static Expense createTimeExpense(String jobId, String name, int hours, int minutes) {
        long total = hours * 60 + minutes;
        Expense expense = new Expense();
        expense.setType(Expense.Type.TIME);
        expense.setName(name);
        expense.setMainId(jobId);
        expense.setCreationDate(new Date());
        expense.setValue(total);
        return expense;
    }

    public static Expense createMaterialExpense(String jobId, String materialName, long materialId, int countOfMaterials) {
        Expense expense = new Expense();
        expense.setMainId(jobId);
        expense.setName(materialName);
        expense.setTypeId(materialId);
        expense.setType(Expense.Type.MATERIAL);
        expense.setCreationDate(new Date());
        expense.setValue(countOfMaterials);
        return expense;
    }
    // endregion

    // region Parcel
    protected Expense(Parcel in) {
        expenseId = in.readLong();
        name = in.readString();
        discount = in.readFloat();
        value = in.readLong();
        unitName = in.readString();
        creationDate = in.readLong();
        type = in.readString();
        typeId = in.readLong();
        mainId = in.readString();
        typedValue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(expenseId);
        dest.writeString(name);
        dest.writeFloat(discount);
        dest.writeLong(value);
        dest.writeString(unitName);
        dest.writeLong(creationDate);
        dest.writeString(type);
        dest.writeLong(typeId);
        dest.writeString(mainId);
        dest.writeString(typedValue);
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
    public void setTypeId(long id) {
        typeId = id;
    }

    @Override
    public long getTypeId() {
        return 0;
    }

    @Override
    public String getMainId() {
        return mainId;
    }

    @Override
    public void setMainId(String id) {
        mainId = id;
    }

    @Override
    public float getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
