package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.AddressRequestORM;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class AddressSource extends EntitySource {

    public Address getAddress(Customer customer) {
        try {
            AddressRequestORM addressRequest = new AddressRequestORM();
            addressRequest.queryForId(customer.getAddressId());
            return dataSource.get(addressRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
            return null;
        }
    }

}
