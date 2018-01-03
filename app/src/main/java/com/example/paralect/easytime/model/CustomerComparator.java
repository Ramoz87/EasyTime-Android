package com.example.paralect.easytime.model;

import java.util.Comparator;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerComparator implements Comparator<Customer> {

    public CustomerComparator() { }

    @Override
    public int compare(Customer customer, Customer t1) {
        if (customer == null && t1 == null) return 0;

        if (customer == null) return -1;

        if (t1 == null) return 1;

        String name1 = customer.getCompanyName();
        String name2 = customer.getCompanyName();

        if (name1 == null && name2 == null) return 0;

        if (name1 == null) return -1;

        if (name2 == null) return 1;

        return name1.compareTo(name2);
    }
}
