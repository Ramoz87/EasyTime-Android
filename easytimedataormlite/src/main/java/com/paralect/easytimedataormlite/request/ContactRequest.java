package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Contact;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.easytimedataormlite.model.ContactEntity;

import java.sql.SQLException;

import static com.example.paralect.easytime.model.Constants.CONTACT_ID;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ContactRequest extends ORMLiteRequest<ContactEntity, Contact> {

    @Override
    public Contact toAppEntity(ContactEntity ex) {
        if (ex == null) return null;
        Contact in = new Contact();
        in.setContactId(ex.getContactId());
        in.setEmail(ex.getEmail());
        in.setFax(ex.getFax());
        in.setFirstName(ex.getFirstName());
        in.setLastName(ex.getLastName());
        in.setPhone(ex.getPhone());
        in.setCustomerId(ex.getCustomerId());
        return in;
    }

    @Override
    public ContactEntity toDataSourceEntity(Contact in) {
        if (in == null) return null;
        ContactEntity ex = new ContactEntity();
        ex.setContactId(in.getContactId());
        ex.setEmail(in.getEmail());
        ex.setFax(in.getFax());
        ex.setFirstName(in.getFirstName());
        ex.setLastName(in.getLastName());
        ex.setPhone(in.getPhone());
        ex.setCustomerId(in.getCustomerId());
        return ex;
    }

    @Override
    public Class<ContactEntity> getDataSourceEntityClazz() {
        return ContactEntity.class;
    }

    @Override
    public Class<Contact> getAppEntityClazz() {
        return Contact.class;
    }

    @Override
    public void queryForId(String id) throws SQLException {
        queryWhere(CONTACT_ID, id);
    }

    @Override
    public void queryForEqual(String id) throws SQLException {
        queryWhere(CUSTOMER_ID, id);
    }
}