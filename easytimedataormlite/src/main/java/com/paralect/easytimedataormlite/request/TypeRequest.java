package com.paralect.easytimedataormlite.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Type;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.easytimedataormlite.model.TypeEntity;

import java.sql.SQLException;

import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.TYPE;
import static com.example.paralect.easytime.model.Constants.TYPE_ID;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class TypeRequest extends ORMLiteRequest<TypeEntity, Type> {

    @Override
    public Type toAppEntity(TypeEntity ex) {
        if (ex == null) return null;

        Type in = new Type();
        in.setName(ex.getName());
        in.setType(ex.getType());
        in.setTypeId(ex.getTypeId());
        return in;
    }

    @Override
    public TypeEntity toDataSourceEntity(Type in) {
        if (in == null) return null;

        TypeEntity ex = new TypeEntity();
        ex.setName(in.getName());
        ex.setType(in.getType());
        ex.setTypeId(in.getTypeId());
        return ex;
    }

    @Override
    public Class<TypeEntity> getDataSourceEntityClazz() {
        return TypeEntity.class;
    }

    @Override
    public Class<Type> getAppEntityClazz() {
        return Type.class;
    }

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, TYPE_ID, id);
    }

    public void queryForList(OrmLiteSqliteOpenHelper helper, @Type.TypeName String type, String searchName) throws SQLException {
        Dao<TypeEntity, ?> dao = helper.getDao(getDataSourceEntityClazz());
        QueryBuilder<TypeEntity, ?> qb = dao.queryBuilder();
        if (!TextUtils.isEmpty(type)) {
            qb.where()
                    .eq(TYPE, type)
                    .and()
                    .like(NAME, "%" + searchName + "%")
                    .prepare();
        }
        setParameter(qb.prepare());
    }
}