package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Order;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class OrderRequestCSV extends JobRequestCSV<Order> {

    public OrderRequestCSV() {
        setQuery("db/orders.csv");
    }

    @Override
    public Order toAppEntity(String[] fields) {
        Order order = null;
        try {
            order = new Order();
            fillJob(order, fields);

            order.setContact(fields[14]);
            order.setDeliveryTime(fields[15]);

            Address address = new Address();
            address.setStreet(fields[16]);
            address.setCity(fields[17]);
            address.setZip(fields[18]);
            order.setAddress(address);

            String objectIds = fields[13];
            objectIds = objectIds.replace("\"", "");
            String[] ids = objectIds.split(",[ ]*");
            if (ids.length == 1 && ids[0].isEmpty()) ids = new String[0];
            order.setObjectIds(ids);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Class<Order> getAppEntityClazz() {
        return Order.class;
    }

}
