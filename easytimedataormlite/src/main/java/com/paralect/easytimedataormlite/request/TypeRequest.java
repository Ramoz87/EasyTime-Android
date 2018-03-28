package com.paralect.easytimedataormlite.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Type;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.easytimedataormlite.model.TypeEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class TypeRequest extends BaseRequest<Type, TypeEntity> {

    @Override
    public Type toInternalEntity(TypeEntity ex) {
        Type in = new Type();
        if (ex != null) {
            in.setName(ex.getName());
            in.setType(ex.getType());
            in.setTypeId(ex.getTypeId());
        }
        return in;
    }

    @Override
    public TypeEntity toExternalEntity(Type in) {
        TypeEntity ex = new TypeEntity();
        if (in != null) {
            ex.setName(in.getName());
            ex.setType(in.getType());
            ex.setTypeId(in.getTypeId());
        }
        return ex;
    }

    @Override
    public Class<Type> getInnerEntityClazz() {
        return Type.class;
    }

    @Override
    public Class<TypeEntity> getExternalEntityClazz() {
        return TypeEntity.class;
    }

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, TypeEntity.ID, id);
    }

    public void queryForList(OrmLiteSqliteOpenHelper helper, @Type.TypeName String type, String searchName) throws SQLException {
        Dao<TypeEntity, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<TypeEntity, ?> qb = dao.queryBuilder();
        if (!TextUtils.isEmpty(type)) {
            qb.where()
                    .eq("type", type)
                    .and()
                    .like("name", "%" + searchName + "%")
                    .prepare();
        }
        setParameter(qb);
    }
}