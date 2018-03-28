package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Contact;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.paralect.easytimedataormlite.model.ContactEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ContactRequest extends BaseRequest<Contact, ContactEntity> {

    @Override
    public Contact toInternalEntity(ContactEntity ex) {
        Contact in = new Contact();
        if (ex != null) {
            in.setContactId(ex.getContactId());
            in.setEmail(ex.getEmail());
            in.setFax(ex.getFax());
            in.setFirstName(ex.getFirstName());
            in.setLastName(ex.getLastName());
            in.setPhone(ex.getPhone());
            in.setCustomerId(ex.getCustomerId());
        }
        return in;
    }

    @Override
    public ContactEntity toExternalEntity(Contact in) {
        ContactEntity ex = new ContactEntity();
        if (in != null) {
            ex.setContactId(in.getContactId());
            ex.setEmail(in.getEmail());
            ex.setFax(in.getFax());
            ex.setFirstName(in.getFirstName());
            ex.setLastName(in.getLastName());
            ex.setPhone(in.getPhone());
            ex.setCustomerId(in.getCustomerId());
        }
        return ex;
    }

    @Override
    public Class<Contact> getInnerEntityClazz() {
        return Contact.class;
    }

    @Override
    public Class<ContactEntity> getExternalEntityClazz() {
        return ContactEntity.class;
    }

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, ContactEntity.ID, id);
    }

    public void queryForEq(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, ContactEntity.CUSTOMER_ID, id);
    }
}