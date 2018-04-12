package com.paralect.database.request;

import com.example.paralect.easytime.model.Contact;
import com.paralect.database.model.ContactEntity;
import com.paralect.datasource.room.RoomRequest;

import static com.example.paralect.easytime.model.Constants.CONTACT_ID;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ContactRequestORM extends RoomRequest<ContactEntity, Contact> {

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
    public void queryForId(String id) throws Exception {
        queryWhere(CONTACT_ID, id);
    }

    @Override
    public void queryForEqual(String id) throws Exception {
        queryWhere(CUSTOMER_ID, id);
    }

    @Override
    public String getTableName() {
        return "contacts";
    }
}