package com.paralect.easytimedataormlite.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.datasource.ormlite.QueryContainer;
import com.paralect.easytimedataormlite.model.CustomerEntity;
import com.paralect.easytimedataormlite.model.JobEntity;

import java.sql.SQLException;
import java.util.Date;

import static com.example.paralect.easytime.model.Constants.CUSTOMER_ID;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.utils.CalendarUtils.SHORT_DATE_FORMAT;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public abstract class BaseJobRequestORM<DS extends JobEntity, AP extends Job> extends ORMLiteRequest<DS, AP> {

    void populateInternalEntity(Job in, JobEntity ex) {
        if (in != null && ex != null) {
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
        }
    }

    void populateExternalEntity(Job in, JobEntity ex) {
        if (in != null && ex != null) {
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
        }
    }

    @Override
    public void queryForId(String id) throws SQLException {
        queryWhere(JOB_ID, id);
    }

    public void queryForList(final String customerId, final String query, final String date) throws SQLException {
        setQueryBuilder(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                QueryBuilder<T, ?> qb = dao.queryBuilder();

                boolean hasCustomerId = !TextUtils.isEmpty(customerId);
                boolean hasQuery = !TextUtils.isEmpty(query);
                boolean hasDate = !TextUtils.isEmpty(date);

                Where where = null;
                if (hasCustomerId) {
                    where = qb.where().eq(CUSTOMER_ID, customerId);
                }

                if (hasQuery) {
                    if (where == null) where = qb.where();
                    else where.and();

                    where.like("name", "%" + query + "%")
                            .or().raw("CAST(number AS TEXT) LIKE '%" + query + "%'");
                }

                if (hasDate) {
                    Date time = CalendarUtils.dateFromString(date, SHORT_DATE_FORMAT);
                    if (where == null) where = qb.where();
                    else where.and();

                    where.le("date", time.getTime());
                }
                return qb.prepare();
            }
        });
    }

    public void queryCountOfCustomers(final String customerId) throws SQLException {
        queryForCount(CUSTOMER_ID, customerId);
    }
}
