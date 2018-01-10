package com.example.paralect.easytime.main.customers.customer;

import android.widget.ListView;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.utils.MiscUtils;

import java.util.ArrayList;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class CustomerPresenter implements IDataPresenter<Customer, Customer>{

    IDataView<Customer> mDataView;

    @Override
    public IDataPresenter<Customer, Customer> setDataView(IDataView<Customer> view) {
        mDataView = view;
        return this;
    }

    @Override
    public IDataPresenter<Customer, Customer> requestData(Customer parameter) {

        return this;
    }

    public static class Container{
        Customer customer;
        ArrayList<Project> projects;
        ArrayList<Order> orders;
        ArrayList<Object> objects;
    }
}
