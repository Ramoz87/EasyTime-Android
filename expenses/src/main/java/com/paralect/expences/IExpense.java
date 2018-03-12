package com.paralect.expences;

import com.paralect.base.Model;

import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 05/03/2018.
 */

public interface IExpense extends Model{

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
     * Unit name of value of this expense, for ex. kg, meters, seconds etc.
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
     * Id of Time, material or any work
     */
    void setTypeId(long id);

    long getTypeId();
    // endregion

    /**
     * Unique Id of job
     */
    String getMainId();

    void setMainId(String id);

    /**
     * Discount
     */
    float getDiscount();

    void setDiscount(float discount);

}
