package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Customer;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.datasource.ormlite.QueryContainer;
import com.paralect.easytimedataormlite.model.CustomerEntity;

import java.sql.SQLException;

import static com.example.paralect.easytime.model.Constants.COMPANY_NAME;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class CustomerRequest extends ORMLiteRequest<CustomerEntity, Customer> {

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
    public void queryForId(String id) throws SQLException {
        queryWhere(CUSTOMER_ID, id);
    }

    public void queryForSearch(final String query) throws SQLException {
        setParameter(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                QueryBuilder<T, ?> qb = dao.queryBuilder();
                qb.where().like(COMPANY_NAME, "%" + query + "%");
                return qb.prepare();
            }
        });
    }
    // endregion
}
