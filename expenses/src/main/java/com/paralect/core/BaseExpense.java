package com.paralect.core;

import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 05/03/2018.
 */

public interface BaseExpense extends Model {

    String EXPENSE_TABLE_NAME = "expenses_table";
    String EXPENSE_ID = "expenseId";
    String NAME = "name";
    String DISCOUNT = "discount";
    String VALUE = "value";
    String UNIT_NAME = "unitName";
    String CREATION_DATE = "creationDate";
    String TYPE = "type";

    /**
     * The name of this expense
     */
    String getName();

    void setName(String name);

    /**
     * Can be any countable value. Time, pieces, kg, meters
     */
    long getValue();

    void setValue(long value);

    /**
     * ExpenseUnit name of value of this expense, for ex. kg, meters, seconds etc.
     */
    String getUnitName();

    void setUnitName(String unitName);

    /**
     * Date when this expense was created
     */
    long getCreationDate();

    void setCreationDate(long date);

    void setCreationDate(Date date);

    // region Type
    /**
     * Type of Time, material or any work
     */
    void setType(String type);

    String getType();

    /**
     * Discount
     */
    float getDiscount();

    void setDiscount(float discount);

}
