package com.paralect.database.request;

import com.example.paralect.easytime.model.Customer;
import com.paralect.database.model.CustomerEntity;
import com.paralect.datasource.database.DatabaseRequestImpl;
import com.paralect.datasource.database.QueryBuilder;

import static com.example.paralect.easytime.model.Constants.COMPANY_NAME;
import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class CustomerRequestORM extends DatabaseRequestImpl<CustomerEntity, Customer> {

    @Override
    public Customer toAppEntity(CustomerEntity ex) {
        if (ex == null) return null;
        Customer in = new Customer();
        in.setCompanyName(ex.getCompanyName());
        in.setId(ex.getCustomerId());
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
        ex.setCustomerId(in.getId());
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

    @Override
    public String getTableName() {
        return "customers";
    }
    // region Requests
    @Override
    public void queryForId(String id) throws Exception {
        queryWhere(CUSTOMER_ID, id);
    }

    @Override
    public void queryForSearch(final String query) throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .select()
                .from(getTableName())
                .where(COMPANY_NAME)
                .like("%" + query + "%");
        setQueryBuilder(queryBuilder);
    }
    // endregion
}
