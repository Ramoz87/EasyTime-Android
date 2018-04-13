package com.paralect.database.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.paralect.database.model.JobEntity;
import com.paralect.datasource.database.DatabaseRequestImpl;
import com.paralect.datasource.database.QueryBuilder;

import java.util.Date;

import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public abstract class BaseJobRequestORM<DS extends JobEntity, AP extends Job> extends DatabaseRequestImpl<DS, AP> {

    void populateInternalEntity(Job in, JobEntity ex) {
        if (in != null && ex != null) {
            in.setCurrency(ex.getCurrency());
            in.setCustomerId(ex.getCustomerId());
            in.setDate(ex.getDate());
            in.setEntityType(ex.getEntityType());
            in.setInformation(ex.getInformation());
            in.setId(ex.getJobId());
            in.setName(ex.getName());
            in.setNumber(ex.getNumber());
            in.setStatusId(ex.getStatusId());
            in.setTypeId(ex.getTypeId());
            in.setDiscount(ex.getDiscount());
            in.setMemberIds(ex.getUserIds());
        }
    }

    void populateExternalEntity(Job in, JobEntity ex) {
        if (in != null && ex != null) {
            ex.setCurrency(in.getCurrency());
            ex.setCustomerId(in.getCustomerId());
            ex.setDate(in.getDate());
            ex.setEntityType(in.getEntityType());
            ex.setInformation(in.getInformation());
            ex.setJobId(in.getId());
            ex.setName(in.getName());
            ex.setNumber(in.getNumber());
            ex.setStatusId(in.getStatusId());
            ex.setTypeId(in.getTypeId());
            ex.setDiscount(in.getDiscount());
            ex.setUserIds(in.getMemberIds());
        }
    }

    @Override
    public void queryForId(String id) throws Exception {
        queryWhere(JOB_ID, id);
    }

    // SELECT * FROM `objects` WHERE `date` <= 1522641600000
    public void queryForList(final String customerId, final String query, final String date) throws Exception {

        boolean hasCustomerId = !TextUtils.isEmpty(customerId);
        boolean hasQuery = !TextUtils.isEmpty(query);
        boolean hasDate = !TextUtils.isEmpty(date);

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.select().from(getTableName());

        if (hasCustomerId) {
            queryBuilder.where(CUSTOMER_ID).eq(customerId);
        }

        if (hasQuery) {

            if (queryBuilder.hasStatement(QueryBuilder.WHERE))
                queryBuilder.and(NAME);
            else
                queryBuilder.where(NAME);

            queryBuilder.like("%" + query + "%").or("CAST(number AS TEXT) LIKE '%" + query + "%'");
        }

        if (hasDate) {
            Date time = CalendarUtils.dateFromString(date, SHORT_DATE_FORMAT);

            if (queryBuilder.hasStatement(QueryBuilder.WHERE))
                queryBuilder.and("date");
            else
                queryBuilder.where("date");

            queryBuilder.lessOrEquals(time.getTime());
        }
        setQueryBuilder(queryBuilder);
    }

    public void queryCountOfCustomers(final String customerId) throws Exception {
        queryForCount(CUSTOMER_ID, customerId);
    }
}
