package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Order;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.OrderEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class OrderRequest extends BaseJobRequest<Order, OrderEntity> {

    @Override
    public Order toInternalEntity(OrderEntity ex) {
        Order in = new Order();
        if (ex != null) {
            populateInternalEntity(in, ex);

            in.setAddressString(ex.getAddressString());
            in.setCityString(ex.getCityString());
            in.setZipString(ex.getZipString());

            in.setContact(ex.getContact());
            in.setDeliveryTime(ex.getDeliveryTime());
            in.setObjectIds(ex.getObjectIds());
        }
        return in;
    }

    @Override
    public OrderEntity toExternalEntity(Order in) {
        OrderEntity ex = new OrderEntity();
        if (in != null) {
            populateExternalEntity(in, ex);

            ex.setAddressString(in.getAddressString());
            ex.setCityString(in.getCityString());
            ex.setZipString(in.getZipString());

            ex.setContact(in.getContact());
            ex.setDeliveryTime(in.getDeliveryTime());
            ex.setObjectIds(in.getObjectIds());
        }
        return ex;
    }

    @Override
    public Class<Order> getInnerEntityClazz() {
        return Order.class;
    }

    @Override
    public Class<OrderEntity> getExternalEntityClazz() {
        return OrderEntity.class;
    }
}
