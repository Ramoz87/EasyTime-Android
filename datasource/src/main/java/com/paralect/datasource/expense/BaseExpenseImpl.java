package com.paralect.datasource.expense;

import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 26/03/2018.
 */

public class BaseExpenseImpl implements BaseExpense {

    protected String id;
    protected String name;
    protected long value;
    protected long creationDate;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
        this.creationDate = creationDate;
    }

    @Override
    public void setCreationDate(Date date) {
        if (date != null)
            creationDate = date.getTime();
    }
}
