package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Order;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.OrderEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class OrderRequest extends EntityRequestImpl<Order, OrderEntity, QueryBuilder<?, ?>> {

    @Override
    public Order toInnerEntity(OrderEntity ex) {
        Order in = new Order();
        if (ex != null) {
            in.setCurrency(ex.getCurrency());
            in.setCustomerId(ex.getCustomerId());
            in.setDate(ex.getDate());
            in.setEntityType(ex.getEntityType());
            in.setInformation(ex.getInformation());
            in.setId(ex.getId());
            in.setName(ex.getName());
            in.setNumber(ex.getNumber());
            in.setStatusId(ex.getStatusId());
            in.setTypeId(ex.getTypeId());
            in.setDiscount(ex.getDiscount());
            in.setMemberIds(ex.getMemberIds());

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
            ex.setCurrency(in.getCurrency());
            ex.setCustomerId(in.getCustomerId());
            ex.setDate(in.getDate());
            ex.setEntityType(in.getEntityType());
            ex.setInformation(in.getInformation());
            ex.setId(in.getId());
            ex.setName(in.getName());
            ex.setNumber(in.getNumber());
            ex.setStatusId(in.getStatusId());
            ex.setTypeId(in.getTypeId());
            ex.setDiscount(in.getDiscount());
            ex.setMemberIds(in.getMemberIds());

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
