package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public interface Model<ID> {

    /**
     * Unique Id of the object
     */
    ID getId();

    void setId(ID id);
}
