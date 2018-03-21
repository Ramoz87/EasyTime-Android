package com.example.paralect.easytime.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.paralect.easytime.model.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.core.DataSource;
import com.paralect.core.Model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ORMLiteDataSource extends DatabaseHelper implements DataSource<QueryBuilder<?, ?>> {

    public ORMLiteDataSource(@NonNull Context context) {
        super(context);
    }

    @Override
    public <M extends Model> M get(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return dao.query(param.prepare()).get(0);
    }

    @Override
    public <M extends Model> List<M> getList(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return param.query();
    }

    public <M extends Model> void save(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.createOrUpdate(model);
    }

    @Override
    public <M extends Model> void update(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.update(model);
    }

    public <M extends Model> void delete(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.delete(model);
    }

}
