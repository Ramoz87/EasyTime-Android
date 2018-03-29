package com.paralect.datasource.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
import java.util.logging.Logger;

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
    public <DS, AP> AP get(EntityRequest<DS, AP, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        QueryBuilder<DS, ?> param = (QueryBuilder<DS, ?>) request.getParameter();
        DS entity = dao.queryForFirst(param.prepare());
        AP result = request.toAppEntity(entity);
        return result;
    }

    @Override
    public <DS, AP> List<AP> getList(EntityRequest<DS, AP, QueryBuilder<?, ?>> request) throws SQLException {
        QueryBuilder<DS, ?> param = (QueryBuilder<DS, ?>) request.getParameter();
        List<DS> entities = param.query();
        List<AP> list = new ArrayList<>();
        for (DS entity : entities) {
            AP result = request.toAppEntity(entity);
            list.add(result);
        }
        return list;
    }

    @Override
    public <DS, AP> void save(EntityRequest<DS, AP, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        DS entity = request.toDataSourceEntity(request.getEntity());
        Dao.CreateOrUpdateStatus status = dao.createOrUpdate(entity);
        Log.d("", "");
    }

    @Override
    public <DS, AP> void update(EntityRequest<DS, AP, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        DS entity = request.toDataSourceEntity(request.getEntity());
        int status = dao.update(entity);
        Log.d("", "");
    }

    @Override
    public <DS, AP> void delete(EntityRequest<DS, AP, QueryBuilder<?, ?>> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        DS entity = request.toDataSourceEntity(request.getEntity());
        int status = dao.delete(entity);
        Log.d("", "");
    }

    public <DS, AP> long count(EntityRequest<DS, AP, QueryBuilder<?, ?>> request) throws SQLException {
        QueryBuilder<DS, ?> param = (QueryBuilder<DS, ?>) request.getParameter();
        param.setCountOf(true);
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        long count = dao.countOf(param.prepare());
        return count;
    }
    // endregion

}
