package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Customer;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.easytimedataormlite.model.CustomerEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class CustomerRequest extends BaseRequest<CustomerEntity, Customer> {

    @Override
    public Customer toAppEntity(CustomerEntity ex) {
        if (ex == null) return null;
        Customer in = new Customer();
        in.setCompanyName(ex.getCompanyName());
        in.setId(ex.getId());
        in.setFirstName(ex.getFirstName());
        in.setLastName(ex.getLastName());
        in.setAddressId(ex.getAddressId());
        return in;
    }

    @Override
    public CustomerEntity toDataSourceEntity(Customer in) {
        if (in == null) return null;
        CustomerEntity ex = new CustomerEntity();
        ex.setCompanyName(in.getCompanyName());
        ex.setId(in.getId());
        ex.setFirstName(in.getFirstName());
        ex.setLastName(in.getLastName());
        ex.setAddressId(in.getAddressId());
        return ex;
    }

    @Override
    public Class<CustomerEntity> getDataSourceEntityClazz() {
        return CustomerEntity.class;
    }

    @Override
    public Class<Customer> getAppEntityClazz() {
        return Customer.class;
    }

    // region Requests
    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, CustomerEntity.ID, id);
    }

    public void queryForSearch(OrmLiteSqliteOpenHelper helper, String query) throws SQLException {
        Dao<CustomerEntity, ?> dao = helper.getDao(CustomerEntity.class);
        QueryBuilder<CustomerEntity, ?> qb = dao.queryBuilder();
        qb.where().like(CustomerEntity.COMPANY_NAME, "%" + query + "%");
        setParameter(qb);
    }
    // endregion
}
