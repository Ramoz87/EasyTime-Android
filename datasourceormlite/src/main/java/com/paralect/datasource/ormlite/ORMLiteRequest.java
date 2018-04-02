package com.paralect.datasource.ormlite;

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

public abstract class ORMLiteRequest<DS, AP> extends EntityRequestImpl<DS, AP, QueryContainer> {

//    public void queryForId(String id) throws SQLException {
//
//    }
//
//    public void queryForId(long id) throws SQLException {
//
//    }

    public <ID> void queryWhere(final String name, final ID value) throws SQLException {
        setParameter(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                QueryBuilder<T, ?> qb = dao.queryBuilder();
                Where where = qb.where();
                where.eq(name, value);
                return qb.prepare();
            }
        });
    }

    public void queryForLast(final String orderByFieldName) throws SQLException {
        setParameter(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                QueryBuilder<T, ?> qb = dao.queryBuilder()
                        .orderBy(orderByFieldName, false)
                        .limit(1L);
                return qb.prepare();
            }
        });
    }

    public void queryForFirst(final String orderByFieldName) throws SQLException {
        setParameter(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                QueryBuilder<T, ?> qb = dao.queryBuilder()
                        .orderBy(orderByFieldName, true)
                        .limit(1L);
                return qb.prepare();
            }
        });
    }

    public void queryForAll() throws SQLException {
        setParameter(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                QueryBuilder<T, ?> qb = dao.queryBuilder();
                return qb.prepare();
            }
        });
    }

    public void deleteWhere(final String name, final String value) throws SQLException {
        setParameter(new QueryContainer() {
            @Override
            public <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException {
                DeleteBuilder<T, ?> query = dao.deleteBuilder();
                query.where().eq(name, value);
                query.delete();
                return query.prepare();
            }
        });
    }

}
