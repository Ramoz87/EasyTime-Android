package com.paralect.database.request;

import android.text.TextUtils;

import com.example.paralect.easytime.model.Type;
import com.paralect.database.model.TypeEntity;
import com.paralect.datasource.database.QueryBuilder;
import com.paralect.datasource.room.RoomRequest;

import java.sql.SQLException;

import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.TYPE;
import static com.example.paralect.easytime.model.Constants.TYPE_ID;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class TypeRequestORM extends RoomRequest<TypeEntity, Type> {

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

    @Override
    public void queryForId(String id) throws Exception {
        queryWhere(TYPE_ID, id);
    }

    public void queryForList(@Type.TypeName final String type, final String searchName) throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.select().from();

        if (!TextUtils.isEmpty(type)) {
            queryBuilder.where(TYPE).eq(type)
                    .and(NAME).like("%" + searchName + "%");
        }
        setQueryBuilder(queryBuilder);
    }

    @Override
    public String getTableName() {
        return "types";
    }
}