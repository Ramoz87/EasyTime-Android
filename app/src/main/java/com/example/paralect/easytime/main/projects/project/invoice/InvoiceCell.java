package com.example.paralect.easytime.main.projects.project.invoice;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oleg Tarashkevich on 30/01/2018.
 */

public interface InvoiceCell {

    @IntDef({Type.HEADER, Type.CELL, Type.TOTAL})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Type {
        int HEADER = 1;
        int CELL = 2;
        int TOTAL = 3;
    }

    String name();

    String value();

    @Type
    int invoiceCellType();

}
