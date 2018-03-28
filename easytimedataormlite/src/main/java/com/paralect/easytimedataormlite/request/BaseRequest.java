package com.paralect.easytimedataormlite.request;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.paralect.datasource.core.EntityRequestImpl;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public abstract class BaseRequest<IN, EX> extends EntityRequestImpl<IN, EX, QueryBuilder<IN, ?>> {

//    public void queryWhere(OrmLiteSqliteOpenHelper helper, String name, String value) throws SQLException {
//        Dao<IN, ?> dao = helper.getDao(getInnerEntityClazz());
//        QueryBuilder<IN, ?> query = dao.queryBuilder();
//        Where where = query.where();
//        where.eq(name, value);
//        setParameter(query);
//    }
//
//    public void deleteWhere(OrmLiteSqliteOpenHelper helper, String name, String value) throws SQLException {
//        Dao<IN, ?> dao = helper.getDao(getInnerEntityClazz());
//        DeleteBuilder<IN, ?> query = dao.deleteBuilder();
//        query.where().eq(name, value);
//        query.delete();
//        setParameter(query);
//    }

}
