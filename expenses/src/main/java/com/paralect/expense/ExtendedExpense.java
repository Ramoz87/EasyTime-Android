package com.paralect.expense;

import com.paralect.core.BaseExpense;

/**
 * Created by Oleg Tarashkevich on 05/03/2018.
 */

public interface ExtendedExpense extends BaseExpense {

    String JOB_ID = "jobId";
    String MATERIAL_ID = "materialId";
    String WORK_TYPE_ID = "workTypeId";

    /**
     * Unique Id of job
     */
    String getJobId();

    void setJobId(String id);

    /**
     * Unique Id of material
     */
    String getMaterialId();

    void setMaterialId(String id);

    boolean isMaterialExpense();

    /**
     * Unique Id of work type
     */
    String getWorkTypeId();

    void setWorkTypeId(String id);

    /**
     * String which contains info about value and unit, for ex. 5 kg, $200
     */
    String getValueWithUnitName();

    void setValueWithUnitName(ExpenseUnit expenseUnitCallback);
    
    void setValueWithUnitName(String valueWithUnitName);

}
