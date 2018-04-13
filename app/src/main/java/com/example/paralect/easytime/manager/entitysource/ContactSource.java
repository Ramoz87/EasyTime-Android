package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.ContactRequestORM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class ContactSource extends EntitySource{

    public List<Contact> getContacts(Customer customer) throws Exception {
        ContactRequestORM contactRequest = new ContactRequestORM();
        contactRequest.queryForEqual(customer.getId());
        return dataSource.getList(contactRequest);
    }
}
