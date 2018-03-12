package com.example.paralect.easytime.manager;


import com.example.paralect.easytime.EasyTimeApplication;
import com.j256.ormlite.dao.Dao;
import com.paralect.base.DataSource;
import com.paralect.expences.ExpenseDataSource;
import com.paralect.sqlite.SQLiteNativeExpenseDataSource;
import com.prilaga.expensesormlite.ORMLiteExpenseDataSource;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

/*

Example of using:

  ExpenseFactory factory = new ExpenseFactory();
  factory.init();

  ExpenseDataSource dataSource = factory.getDataSource(ORMLITE);
  List<Expense> expenses = dataSource.getExpenses();

 */

public class ExpenseFactory extends DataSource.Factory<ExpenseDataSource> {

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
    public ExpenseDataSource onCreateDataSource(int dataSourceType) {
        switch (dataSourceType) {
            case SQLITE_NATIVE:
                return new SQLiteNativeExpenseDataSource();
            case ORMLITE:
                try {
                    Dao<com.prilaga.expensesormlite.Expense, Long> dao =
                            EasyTimeManager.getInstance().getHelper().getExpenseDaoModule();
                    return new ORMLiteExpenseDataSource(EasyTimeApplication.getContext(), dao);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

}
