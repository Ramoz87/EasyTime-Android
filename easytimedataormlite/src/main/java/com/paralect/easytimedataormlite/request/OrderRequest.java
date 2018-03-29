package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Order;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.OrderEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class OrderRequest extends BaseJobRequest<OrderEntity, Order> {

    @Override
    public Order toAppEntity(OrderEntity ex) {
        if (ex == null) return null;

        Order in = new Order();
        populateInternalEntity(in, ex);

        in.setAddressString(ex.getAddressString());
        in.setCityString(ex.getCityString());
        in.setZipString(ex.getZipString());

        in.setContact(ex.getContact());
        in.setDeliveryTime(ex.getDeliveryTime());
        in.setObjectIds(ex.getObjectIds());
        return in;
    }

    @Override
    public OrderEntity toDataSourceEntity(Order in) {
        if (in == null) return null;

        OrderEntity ex = new OrderEntity();
        populateExternalEntity(in, ex);

        ex.setAddressString(in.getAddressString());
        ex.setCityString(in.getCityString());
        ex.setZipString(in.getZipString());

        ex.setContact(in.getContact());
        ex.setDeliveryTime(in.getDeliveryTime());
        ex.setObjectIds(in.getObjectIds());
        return ex;
    }

    @Override
    public Class<OrderEntity> getDataSourceEntityClazz() {
        return OrderEntity.class;
    }

    @Override
    public Class<Order> getAppEntityClazz() {
        return Order.class;
    }
}
