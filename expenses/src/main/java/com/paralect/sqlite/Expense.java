package com.paralect.sqlite;

import com.paralect.expences.IExpense;

import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class Expense implements IExpense {

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public long getValue() {
        return 0;
    }

    @Override
    public void setValue(long value) {

    }

    @Override
    public String getUnitName() {
        return null;
    }

    @Override
    public void setUnitName(String unitName) {

    }

    @Override
    public long getCreationDate() {
        return 0;
    }

    @Override
    public void setCreationDate(long date) {

    }

    @Override
    public void setCreationDate(Date date) {

    }

    @Override
    public void setType(String type) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setTypeId(long id) {

    }

    @Override
    public long getTypeId() {
        return 0;
    }

    @Override
    public String getMainId() {
        return null;
    }

    @Override
    public void setMainId(String id) {

    }

    @Override
    public float getDiscount() {
        return 0;
    }

    @Override
    public void setDiscount(float discount) {

    }
}
