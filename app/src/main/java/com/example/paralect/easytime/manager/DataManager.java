package com.example.paralect.easytime.manager;

import com.example.paralect.easytime.EasyTimeApplication;
import com.paralect.database.DatabaseHelper;

/**
 * Created by alexei on 26.12.2017.
 */

public final class DataManager {

    private volatile static DataManager instance;
    private DatabaseHelper dataSource;

    /**
     * Returns singleton class instance
     */
    public static DataManager getInstance() {
        DataManager localInstance = instance;

        if (localInstance == null) {
            synchronized (DataManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataManager();
                }
            }
        }
        return localInstance;
    }

    private DataManager() {
        if (dataSource == null)
            dataSource = new DatabaseHelper(EasyTimeApplication.getContext());
    }

    public DatabaseHelper getDataSource() {
        return dataSource;
    }

    // TODO: Do we need to manage all data access from one place?
//    private AddressSource addressSource = new AddressSource();
//    private ContactSource contactSource = new ContactSource();
//    private CSVSource csvSource = new CSVSource();
//    private CustomerSource customerSource = new CustomerSource();
//    private ExpenseSource expenseSource = new ExpenseSource();
//    private FileSource fileSource = new FileSource();
//    private JobSource jobSource = new JobSource();
//    private MaterialsSource materialsSource = new MaterialsSource();
//    private TypeSource typeSource = new TypeSource();
//    private UserSource userSource = new UserSource();

}
