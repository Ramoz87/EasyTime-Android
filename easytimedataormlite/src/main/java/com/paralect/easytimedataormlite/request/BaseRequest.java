package com.paralect.easytimedataormlite.request;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.datasource.core.EntityRequestImpl;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public abstract class BaseRequest<IN, EX> extends EntityRequestImpl<IN, EX, QueryBuilder<?, ?>> {

    public void queryWhere(OrmLiteSqliteOpenHelper helper, String name, String value) throws SQLException {
        Dao<EX, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<EX, ?> query = dao.queryBuilder();
        Where where = query.where();
        where.eq(name, value);
        setParameter(query);
    }

    public void queryForLast(OrmLiteSqliteOpenHelper helper, String orderByFieldName) throws SQLException {
        Dao<EX, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<EX, ?> qb = dao.queryBuilder()
                .orderBy(orderByFieldName, false)
                .limit(1L);
        setParameter(qb);
    }

    public void queryForFirst(OrmLiteSqliteOpenHelper helper, String orderByFieldName) throws SQLException {
        Dao<EX, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<EX, ?> qb = dao.queryBuilder()
                .orderBy(orderByFieldName, true)
                .limit(1L);
        setParameter(qb);
    }

    public void queryForAll(OrmLiteSqliteOpenHelper helper)throws SQLException{
        Dao<EX, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<EX, ?> qb = dao.queryBuilder();
        setParameter(qb);
    }

//    public void deleteWhere(OrmLiteSqliteOpenHelper helper, String name, String value) throws SQLException {
//        Dao<IN, ?> dao = helper.getDao(getInnerEntityClazz());
//        DeleteBuilder<IN, ?> query = dao.deleteBuilder();
//        query.where().eq(name, value);
//        query.delete();
//        setParameter(query);
//    }

}
