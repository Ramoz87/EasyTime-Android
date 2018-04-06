package com.example.paralect.easytime.manager.entitysource;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.JobWithAddress;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.datacsv.CSVHelper;
import com.paralect.datacsv.request.CustomerRequestCSV;
import com.paralect.datacsv.request.MaterialRequestCSV;
import com.paralect.datacsv.request.ObjectRequestCSV;
import com.paralect.datacsv.request.OrderRequestCSV;
import com.paralect.datacsv.request.ProjectsRequestCSV;
import com.paralect.datacsv.request.TypeRequestCSV;
import com.paralect.datacsv.request.UserRequestCSV;
import com.paralect.datasource.core.EntityRequest;
import com.paralect.easytimedataormlite.request.AddressRequestORM;
import com.paralect.easytimedataormlite.request.ContactRequestORM;
import com.paralect.easytimedataormlite.request.CustomerRequestORM;
import com.paralect.easytimedataormlite.request.MaterialRequestORM;
import com.paralect.easytimedataormlite.request.ObjectRequestORM;
import com.paralect.easytimedataormlite.request.OrderRequestORM;
import com.paralect.easytimedataormlite.request.ProjectRequestORM;
import com.paralect.easytimedataormlite.request.TypeRequestORM;
import com.paralect.easytimedataormlite.request.UserRequestORM;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class CSVSource extends EntitySource {

    public void populateData(AssetManager assetManager) {
        try {
            CSVHelper csvHelper = new CSVHelper(assetManager);

            List<User> users = csvHelper.getList(new UserRequestCSV());
            fillData(users, new UserRequestORM());

            List<Type> types = csvHelper.getList(new TypeRequestCSV());
            fillData(types, new TypeRequestORM());

            List<Customer> customers = csvHelper.getList(new CustomerRequestCSV());
            fillData(customers, new CustomerRequestORM());

            List<Material> materials = csvHelper.getList(new MaterialRequestCSV());
            fillData(materials, new MaterialRequestORM());

            List<Object> objects = csvHelper.getList(new ObjectRequestCSV());
            fillData(objects, new ObjectRequestORM());

            List<Order> orders = csvHelper.getList(new OrderRequestCSV());
            fillData(orders, new OrderRequestORM());

            List<Project> projects = csvHelper.getList(new ProjectsRequestCSV());
            fillData(projects, new ProjectRequestORM());
        } catch (IOException e) {

        }
    }

    protected <E> void fillData(List<E> items, EntityRequest entityRequest) {

        String className = entityRequest.getAppEntityClazz().getSimpleName();
        Log.d(TAG, String.format("===// %s //===", className));

        AddressRequestORM addressRequest = new AddressRequestORM();
        for (E item : items) {

            try {
                Log.d(TAG, item.toString());

                if (item instanceof JobWithAddress) {
                    JobWithAddress job = (JobWithAddress) item;
                    Address address = job.getAddress();
                    addressRequest.setEntity(address);
                    dataSource.saveOrUpdate(addressRequest);
                    job.setAddressId(address.getAddressId());
                }

                if (item instanceof Customer) {

                    ContactRequestORM contactRequest = new ContactRequestORM();
                    Customer customer = (Customer) item;
                    List<Contact> contacts = customer.getContacts();
                    for (Contact contact : contacts) {
                        Log.d(TAG, "ContactEntity: " + contact);
                        contactRequest.setEntity(contact);
                        dataSource.saveOrUpdate(contactRequest);
                    }
                    Address address = customer.getAddress();
                    addressRequest.setEntity(address);
                    dataSource.saveOrUpdate(addressRequest);
                    customer.setAddressId(address.getAddressId());
                }
                entityRequest.setEntity(item);
                dataSource.saveOrUpdate(entityRequest);

            } catch (Throwable e) {
                Logger.e(TAG, e.getMessage());
            }
        }
        Log.d(TAG, "filled " + className + " class");

    }

}