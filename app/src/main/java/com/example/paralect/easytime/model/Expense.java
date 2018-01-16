package com.example.paralect.easytime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "expenses")
public class Expense implements Parcelable {

    @DatabaseField(columnName = "discount", dataType = DataType.FLOAT)
    private float discount;

    @DatabaseField(columnName = "expensiveId", generatedId = true)
    private long expensiveId;

    @DatabaseField(columnName = "materialId")
    private String materialId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "value")
    private int value;

    @DatabaseField(columnName = "workTypeId")
    private String workTypeId;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Object object;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Order order;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Project project;

    private File photo;

    public Expense() {

    }

    protected Expense(Parcel in) {
        discount = in.readFloat();
        expensiveId = in.readLong();
        materialId = in.readString();
        name = in.readString();
        type = in.readString();
        value = in.readInt();
        workTypeId = in.readString();
        object = in.readParcelable(Object.class.getClassLoader());
        order = in.readParcelable(Order.class.getClassLoader());
        project = in.readParcelable(Project.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(discount);
        dest.writeLong(expensiveId);
        dest.writeString(materialId);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(value);
        dest.writeString(workTypeId);
        dest.writeParcelable(object, flags);
        dest.writeParcelable(order, flags);
        dest.writeParcelable(project, flags);
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

    public long getExpensiveId() {
        return expensiveId;
    }

    public void setExpensiveId(long expensiveId) {
        this.expensiveId = expensiveId;
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

    public Job getJob() {
        if (object != null) return object;
        else if (order != null) return order;
        else return project;
    }

    public void setJob(Job job) {
        if (job instanceof Object) {
            this.object = (Object) job;
            this.order = null;
            this.project = null;
        } else if (job instanceof Order) {
            this.object = null;
            this.order = (Order) job;
            this.project = null;
        } else if (job instanceof Project) {
            this.object = null;
            this.order = null;
            this.project = (Project) job;
        }
    }
}
