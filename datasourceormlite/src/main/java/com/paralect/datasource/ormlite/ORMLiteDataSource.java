package com.paralect.datasource.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.DataSource;
import com.paralect.datasource.core.EntityRequest;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public abstract class ORMLiteDataSource extends OrmLiteSqliteOpenHelper implements DataSource<QueryBuilder<?, ?>> {

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

    // region Synchronous access
    @Override
    public <IN, EX> IN get(EntityRequest<IN, EX, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<EX, ?> dao = getDao(request.getExternalEntityClazz());
        QueryBuilder<EX, ?> param = (QueryBuilder<EX, ?>) request.getParameter();
        EX entity = dao.query(param.prepare()).get(0);
        IN result = request.toInnerEntity(entity);
        return result;
    }

    @Override
    public <IN, EX> List<IN> getList(EntityRequest<IN, EX, QueryBuilder<?, ?>> request) throws SQLException {
        QueryBuilder<EX, ?> param = (QueryBuilder<EX, ?>) request.getParameter();
        List<EX> entities = param.query();
        List<IN> list = new ArrayList<>();
        for (EX entity : entities) {
            IN result = request.toInnerEntity(entity);
            list.add(result);
        }
        return list;
    }

    @Override
    public <IN, EX> void save(EntityRequest<IN, EX, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<EX, ?> dao = getDao(request.getExternalEntityClazz());
        EX entity = request.toExternalEntity(request.getEntity());
        dao.createOrUpdate(entity);
    }

    @Override
    public <IN, EX> void update(EntityRequest<IN, EX, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<EX, ?> dao = getDao(request.getExternalEntityClazz());
        EX entity = request.toExternalEntity(request.getEntity());
        dao.update(entity);
    }

    @Override
    public <IN, EX> void delete(EntityRequest<IN, EX, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<EX, ?> dao = getDao(request.getExternalEntityClazz());
        EX entity = request.toExternalEntity(request.getEntity());
        dao.delete(entity);
    }
    // endregion

}
