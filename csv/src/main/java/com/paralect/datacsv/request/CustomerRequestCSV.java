package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Type;
import com.paralect.datasource.core.EntityRequestImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class CustomerRequestCSV extends EntityRequestImpl<String[], Customer, Object> {

    public CustomerRequestCSV() {
        setQuery("db/customers.csv");
    }

    @Override
    public Customer toAppEntity(String[] fields) {
        Customer customer = new Customer();
        customer.setId(fields[21]);
        customer.setCompanyName(fields[38]);
        customer.setFirstName(fields[53]);
        customer.setLastName(fields[38]);

        Contact c = new Contact();
        c.setCustomerId(customer.getId());
        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setEmail(fields[23]);
        c.setFax(fields[25]);
        c.setPhone(fields[47]);

        Contact c1 = new Contact();
        c1.setCustomerId(customer.getId());
        c1.setFirstName(fields[57]);
        c1.setLastName(fields[58]);
        c1.setEmail(getValidString(fields[55]));
        c1.setPhone(getValidString(fields[54]));

        Contact c2 = new Contact();
        c2.setCustomerId(customer.getId());
        c2.setFirstName(fields[62]);
        c2.setLastName(fields[63]);
        c2.setEmail(getValidString(fields[60]));
        c2.setPhone(getValidString(fields[59]));

        Address address = new Address();
        address.setCity(fields[39]);
        address.setStreet(fields[15]);
        address.setZip(fields[41]);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(c);
        contacts.add(c1);
        contacts.add(c2);
        customer.setContacts(contacts);
        customer.setAddress(address);
        return customer;
    }

    private String getValidString(String s) {
        if (s.trim().isEmpty()) return ""; // s contains only whitespaces
        else return s;
    }

    @Override
    public String[] toDataSourceEntity(Customer customer) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

    @Override
    public Class<Customer> getAppEntityClazz() {
        return Customer.class;
    }

}
