package com.paralect.database.request;

import com.example.paralect.easytime.model.Address;
import com.paralect.database.model.AddressEntity;
import com.paralect.datasource.database.DatabaseRequestImpl;

import static com.example.paralect.easytime.model.Constants.ADDRESS_ID;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class AddressRequestORM extends DatabaseRequestImpl<AddressEntity, Address> {

    @Override
    public Address toAppEntity(AddressEntity ex) {
        if (ex == null) return null;
        Address in = new Address();
        in.setAddressId(ex.getAddressId());
        in.setCity(ex.getCity());
        in.setCountry(ex.getCountry());
        in.setStreet(ex.getStreet());
        in.setZip(ex.getZip());
        in.setCustomerId(ex.getCustomerId());
        return in;
    }

    @Override
    public AddressEntity toDataSourceEntity(Address in) {
        if (in == null) return null;
        AddressEntity ex = new AddressEntity();
        ex.setAddressId(in.getAddressId());
        ex.setCity(in.getCity());
        ex.setCountry(in.getCountry());
        ex.setStreet(in.getStreet());
        ex.setZip(in.getZip());
        ex.setCustomerId(in.getCustomerId());
        return ex;
    }

    @Override
    public Class<AddressEntity> getDataSourceEntityClazz() {
        return AddressEntity.class;
    }

    @Override
    public Class<Address> getAppEntityClazz() {
        return Address.class;
    }

    // region Requests
    @Override
    public void queryForId(long id) throws Exception {
        queryWhere(ADDRESS_ID, id);
    }

    @Override
    public String getTableName() {
        return "Addresses";
    }
    // endregion
}
