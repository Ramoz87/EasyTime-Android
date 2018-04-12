package com.example.paralect.easytime.manager.entitysource;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.CustomerRequestORM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class CustomerSource extends EntitySource {

    public List<Customer> getCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        try {

            CustomerRequestORM customerRequest = new CustomerRequestORM();

            if (TextUtils.isEmpty(query))
                customerRequest.queryForAll();
            else
                customerRequest.queryForSearch(query);

            customers = dataSource.getList(customerRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return customers;
    }
}
