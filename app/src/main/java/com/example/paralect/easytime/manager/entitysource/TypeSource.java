package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.TypeRequestORM;

import java.sql.SQLException;
import java.util.List;

import static com.example.paralect.easytime.model.Type.TypeName.STATUS;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class TypeSource extends EntitySource {

    // region Type
    public Type getType(String typeId) {
        Type type = null;
        try {
            TypeRequestORM typeRequest = new TypeRequestORM();
            typeRequest.queryForId(typeId);
            type = dataSource.get(typeRequest);
        } catch (Exception exc) {
            Logger.e(exc);
        }
        return type;
    }

    public List<Type> getStatuses() throws Exception{
        return getTypes(STATUS, "");
    }

    public List<Type> getTypes() throws Exception{
        return getTypes(null, "");
    }

    public List<Type> getTypes(@Type.TypeName String type, String searchName) throws Exception{
        TypeRequestORM typeRequest = new TypeRequestORM();
        typeRequest.queryForList(type, searchName);
        return dataSource.getList(typeRequest);
    }
    // endregion

}
