package com.example.paralect.easytime.main.customers.customer;

/**
 * Created by Oleg Tarashkevich on 11/01/2018.
 */

interface ICustomerDataView<J> {

    void onJobsReceived(J jobs);
}
