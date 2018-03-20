package com.example.paralect.easytime.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.paralect.easytime.model.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.core.DataSource;
import com.paralect.core.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleg Tarashkevich on 06/03/2018.
 */

public class ORMLiteDataSourceDev<M extends Model, ID, C extends Class<M>> extends DatabaseHelper implements DataSource<M, Class<M>, QueryBuilder<M, ID>> {

    public ORMLiteDataSourceDev(@NonNull Context context) {
        super(context);
    }

    public <M extends Model> M getModel(Class<M> type, QueryBuilder<M, ?> parameter) throws Throwable {
        Dao<M, ?> dao = getDao(type);
        return dao.query(parameter.prepare()).get(0);
    }


//    public Dao<Class, ID> getDao(Class clazz){
//        Map<Class, Dao<Class, ID>> daoMap
//        if (daoMap.containsKey(clazz))
//
//    }

//    // region Basic methods
//    @Override
//    public Expense getModel(QueryBuilder<Expense, Long> parameter) throws SQLException {
//        return dao.query(parameter.prepare()).get(0);
//    }
//
//    @Override
//    public List<Expense> getModels(QueryBuilder<Expense, Long> qb) throws SQLException {
//        return qb.query();
//    }
//
//    @Override
//    public void saveModel(Expense expense) throws SQLException {
//        dao.createOrUpdate(expense);
//    }
//
//    @Override
//    public void deleteModel(Expense expense) throws SQLException {
//        dao.delete(expense);
//    }
//




    // endregion



//    public static <M extends Model, C extends Class<M>>  Dao<C, java.lang.Object> getDao(M clazz){
//        return null;
//    }
}
