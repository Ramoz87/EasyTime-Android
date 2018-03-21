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

public class ORMLiteDataSourceDev extends DatabaseHelper implements DataSource<QueryBuilder<?, ?>> {

    public ORMLiteDataSourceDev(@NonNull Context context) {
        super(context);
    }

    @Override
    public <M extends Model> M getModel(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return dao.query(param.prepare()).get(0);
    }

    @Override
    public <M extends Model> List<M> getModels(Class<M> type, QueryBuilder<?, ?> parameter) throws SQLException {
        QueryBuilder<M, ?> param = (QueryBuilder<M, ?>) parameter;
        return param.query();
    }

    public <M extends Model> void saveModel(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.createOrUpdate(model);
    }

    public <M extends Model> void deleteModel(Class<M> type, M model) throws SQLException {
        Dao<M, ?> dao = getDao(type);
        dao.delete(model);
    }

}
