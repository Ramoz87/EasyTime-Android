package com.paralect.datasource.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.stmt.PreparedUpdate;
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

public abstract class ORMLiteDataSource extends OrmLiteSqliteOpenHelper implements DataSource<QueryContainer> {

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
    public <DS, AP> AP get(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        PreparedQuery<DS> param = (PreparedQuery<DS>) request.getParameter().getQuery(dao);
        DS entity = dao.queryForFirst(param);
        AP result = request.toAppEntity(entity);
        return result;
    }

    @Override
    public <DS, AP> List<AP> getList(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        PreparedQuery<DS> param = (PreparedQuery<DS>) request.getParameter().getQuery(dao);
        List<DS> entities = dao.query(param);
        List<AP> list = new ArrayList<>();
        for (DS entity : entities) {
            AP result = request.toAppEntity(entity);
            list.add(result);
        }
        return list;
    }

    @Override
    public <DS, AP> void save(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        DS entity = request.toDataSourceEntity(request.getEntity());
        int status = dao.create(entity);
        Log.d("", "");
    }

    @Override
    public <DS, AP> void saveOrUpdate(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        DS entity = request.toDataSourceEntity(request.getEntity());
        Dao.CreateOrUpdateStatus status = dao.createOrUpdate(entity);
        Log.d("", "");
    }

    @Override
    public <DS, AP> void update(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        AP appEntity = request.getEntity();
        QueryContainer container = request.getParameter();

        int status = -1;
        if (appEntity != null) {
            DS entity = request.toDataSourceEntity(request.getEntity());
            status = dao.update(entity);
        } else if (container != null) {
            PreparedStmt<?> statement = container.getQuery(dao);
            if (statement instanceof PreparedUpdate)
                status = dao.update((PreparedUpdate) statement);
        }

        Log.d("", "");
    }

    @Override
    public <DS, AP> void delete(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        DS entity = request.toDataSourceEntity(request.getEntity());
        int status = dao.delete(entity);
        Log.d("", "");
    }

    @Override
    public <DS, AP> long count(EntityRequest<DS, AP, QueryContainer> request) throws SQLException {
        Dao<DS, ?> dao = getDao(request.getDataSourceEntityClazz());
        PreparedQuery<DS> param = (PreparedQuery<DS>) request.getParameter().getQuery(dao);
        long count = dao.countOf(param);
        return count;
    }
    // endregion

}
