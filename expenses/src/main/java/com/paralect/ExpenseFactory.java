package com.paralect;

import com.paralect.base.DataSource;
import com.paralect.expences.IExpenseDataSource;
import com.paralect.ormlite.ORMLiteExpenseDataSource;
import com.paralect.sqlite.SQLiteNativeExpenseDataSource;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ExpenseFactory extends DataSource.Factory<IExpenseDataSource> {

    public static final int NONE = 0;
    public static final int SQLITE_NATIVE = 1;
    public static final int ORMLITE = 2;

    @Override
    public int getDataSourceCount() {
        return 2;
    }

    @Override
    public int getDataSourceType(int position) {
        switch (position) {
            case 0:
                return SQLITE_NATIVE;
            case 1:
                return ORMLITE;
            default:
                return NONE;
        }
    }

    @Override
    public IExpenseDataSource onCreateDataSource(int dataSourceType) {
        switch (dataSourceType) {
            case SQLITE_NATIVE:
                return new SQLiteNativeExpenseDataSource();
            case ORMLITE:
                return new ORMLiteExpenseDataSource();
        }
        return null;
    }

}
