package com.paralect.datasource.expense;

import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 05/03/2018.
 */

public interface BaseExpense {

    /**
     * Unique Id of the object
     */
    long getId();

    void setId(long id);

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
     * Date when this expense was created
     */
    long getCreationDate();

    void setCreationDate(long date);

    void setCreationDate(Date date);

}
