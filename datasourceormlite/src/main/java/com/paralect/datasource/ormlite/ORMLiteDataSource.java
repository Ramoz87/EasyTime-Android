package com.paralect.datasource.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.DataSource;
import com.paralect.datasource.core.Entity;
import com.paralect.datasource.core.EntityRequest;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class ORMLiteDataSource extends OrmLiteSqliteOpenHelper implements DataSource<QueryBuilder<?, ?>>{

    // region Constructors
    public ORMLiteDataSource(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    public ORMLiteDataSource(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, int configFileId) {
        super(context, databaseName, factory, databaseVersion, configFileId);
    }

    public ORMLiteDataSource(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, File configFile) {
        super(context, databaseName, factory, databaseVersion, configFile);
    }

    public ORMLiteDataSource(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, InputStream stream) {
        super(context, databaseName, factory, databaseVersion, stream);
    }
    // endregion

    // region Non synchronous
    @Override
    public <M extends Entity> M get(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return dao.query(param.prepare()).get(0);
    }

    @Override
    public <M extends Entity> List<M> getList(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return param.query();
    }

    public <M extends Entity> void save(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.createOrUpdate(model);
    }

    @Override
    public <M extends Entity> void update(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.update(model);
    }

    public <M extends Entity> void delete(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.delete(model);
    }
    // endregion


    public <IN, EX> IN get(EntityRequest<IN, EX, QueryBuilder<EX, ?>> request) throws SQLException {
        Dao<EX, ?> dao = getDao(request.getExternalClazz());
        QueryBuilder<EX, ?> param = request.getParameter();
        EX entity = dao.query(param.prepare()).get(0);
        IN result = request.wrap(entity);
        return result;
    }

    public <IN, EX> List<IN> getList(EntityRequest<IN, EX, QueryBuilder<EX, ?>> request) throws SQLException {
        QueryBuilder<EX, ?> param = request.getParameter();
        List<EX> entities = param.query();
        List<IN> list = new ArrayList<>();
        for (EX entity : entities) {
            IN result = request.wrap(entity);
            list.add(result);
        }
        return list;
    }

}
