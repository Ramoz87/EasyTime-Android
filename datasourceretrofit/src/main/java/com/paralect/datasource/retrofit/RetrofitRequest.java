package com.paralect.datasource.retrofit;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.datasource.core.EntityRequestImpl;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public abstract class RetrofitRequest<DS, AP> extends EntityRequestImpl<DS, AP, PreparedStmt<?>> {

    public <ID> void queryWhere(OrmLiteSqliteOpenHelper helper, String name, ID value) throws SQLException {
        Dao<DS, ?> dao = helper.getDao(getDataSourceEntityClazz());
        QueryBuilder<DS, ?> query = dao.queryBuilder();
        Where where = query.where();
        where.eq(name, value);
        setParameter(query.prepare());
    }

    public void queryForLast(OrmLiteSqliteOpenHelper helper, String orderByFieldName) throws SQLException {
        Dao<DS, ?> dao = helper.getDao(getDataSourceEntityClazz());
        QueryBuilder<DS, ?> qb = dao.queryBuilder()
                .orderBy(orderByFieldName, false)
                .limit(1L);
        setParameter(qb.prepare());
    }

    public void queryForFirst(OrmLiteSqliteOpenHelper helper, String orderByFieldName) throws SQLException {
        Dao<DS, ?> dao = helper.getDao(getDataSourceEntityClazz());
        QueryBuilder<DS, ?> qb = dao.queryBuilder()
                .orderBy(orderByFieldName, true)
                .limit(1L);
        setParameter(qb.prepare());
    }

    public void queryForAll(OrmLiteSqliteOpenHelper helper) throws SQLException {
        Dao<DS, ?> dao = helper.getDao(getDataSourceEntityClazz());
        QueryBuilder<DS, ?> qb = dao.queryBuilder();
        setParameter(qb.prepare());
    }

    public void deleteWhere(OrmLiteSqliteOpenHelper helper, String name, String value) throws SQLException {
        Dao<DS, ?> dao = helper.getDao(getDataSourceEntityClazz());
        DeleteBuilder<DS, ?> query = dao.deleteBuilder();
        query.where().eq(name, value);
        query.delete();
        setParameter(query.prepare());
    }

}
