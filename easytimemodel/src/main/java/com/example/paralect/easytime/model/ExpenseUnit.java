package com.example.paralect.easytime.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oleg Tarashkevich on 14/03/2018.
 */

public interface ExpenseUnit {

    @StringDef({Type.TIME, Type.MATERIAL, Type.DRIVING, Type.OTHER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Type {
        String TIME = "Time";
        String MATERIAL = "MaterialEntity";
        String DRIVING = "Driving";
        String OTHER = "Other";
    }

    String getTimeUnit();

    String getDrivingUnit();

    String getOtherUnit();

    String getMaterialUnit();
}
