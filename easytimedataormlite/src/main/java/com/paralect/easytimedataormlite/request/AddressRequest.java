package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Address;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.paralect.easytimedataormlite.model.AddressEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class AddressRequest extends BaseRequest<Address, AddressEntity> {

    @Override
    public Address toInternalEntity(AddressEntity ex) {
        Address in = new Address();
        if (ex != null) {
            in.setAddressId(ex.getAddressId());
            in.setCity(ex.getCity());
            in.setCountry(ex.getCountry());
            in.setStreet(ex.getStreet());
            in.setZip(ex.getZip());
            in.setCustomerId(ex.getCustomerId());
        }
        return in;
    }

    @Override
    public AddressEntity toExternalEntity(Address in) {
        AddressEntity ex = new AddressEntity();
        if (in != null) {
            ex.setAddressId(in.getAddressId());
            ex.setCity(in.getCity());
            ex.setCountry(in.getCountry());
            ex.setStreet(in.getStreet());
            ex.setZip(in.getZip());
            ex.setCustomerId(in.getCustomerId());
        }
        return ex;
    }

    @Override
    public Class<Address> getInnerEntityClazz() {
        return Address.class;
    }

    @Override
    public Class<AddressEntity> getExternalEntityClazz() {
        return AddressEntity.class;
    }

    // region Requests
    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, AddressEntity.ID, id);
    }
    // endregion
}
