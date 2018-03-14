package com.paralect.expense;

import android.support.annotation.StringDef;

import com.paralect.core.BaseExpense;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oleg Tarashkevich on 05/03/2018.
 */

public interface ExtendedExpense extends BaseExpense {

    @StringDef({Type.TIME, Type.MATERIAL, Type.DRIVING, Type.OTHER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Type {
        String TIME = "Time";
        String MATERIAL = "Material";
        String DRIVING = "Driving";
        String OTHER = "Other";
    }

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

    /**
     * Unique Id of work type
     */
    String getWorkTypeId();

    void setWorkTypeId(String id);

}
