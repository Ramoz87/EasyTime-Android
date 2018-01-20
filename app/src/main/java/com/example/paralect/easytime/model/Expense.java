package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "expenses")
public class Expense implements Parcelable {

    @StringDef({Type.TIME, Type.MATERIAL, Type.DRIVING, Type.OTHER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Type {
        String TIME = "Time";
        String MATERIAL = "Material";
        String DRIVING = "Driving";
        String OTHER = "Other";
    }

    @DatabaseField(columnName = "discount", dataType = DataType.FLOAT)
    private float discount;

    @DatabaseField(columnName = "expenseId", generatedId = true)
    private long expenseId;

    @DatabaseField(columnName = "materialId")
    private String materialId;

    /* to simplify the access to material of expense we need to hold reference to that material */
    private Material material;

    @DatabaseField(columnName = "name")
    private String name;

    @Type
    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "value")
    private int value;

    @DatabaseField(columnName = "workTypeId")
    private String workTypeId;

    @DatabaseField(columnName = "jobId")
    private String jobId;

    public static Expense reCreate(Expense ex) {
        Expense expense = new Expense();
        if (ex != null) {
            expense.setDiscount(ex.getDiscount());
            expense.setJobId(ex.getJobId());
            expense.setName(ex.getName());
            expense.setType(ex.getType());
            expense.setWorkTypeId(ex.getWorkTypeId());
        }
        return expense;
    }

    public Expense() {

    }

    protected Expense(Parcel in) {
        discount = in.readFloat();
        expenseId = in.readLong();
        materialId = in.readString();
        material = in.readParcelable(Material.class.getClassLoader());
        name = in.readString();
        type = in.readString();
        value = in.readInt();
        workTypeId = in.readString();
        jobId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(discount);
        dest.writeLong(expenseId);
        dest.writeString(materialId);
        dest.writeParcelable(material, flags);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(value);
        dest.writeString(workTypeId);
        dest.writeString(jobId);
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

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(String workTypeId) {
        this.workTypeId = workTypeId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isMaterialExpense() {
        return materialId != null;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
