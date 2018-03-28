package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Material;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.easytimedataormlite.model.MaterialEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class MaterialRequest extends BaseRequest<Material, MaterialEntity> {

    @Override
    public Material toInternalEntity(MaterialEntity ex) {
        Material in = new Material();
        if (ex != null) {
            in.setCurrency(ex.getCurrency());
            in.setMaterialId(ex.getMaterialId());
            in.setMaterialNr(ex.getMaterialNr());
            in.setName(ex.getName());
            in.setPricePerUnit(ex.getPricePerUnit());
            in.setSerialNr(ex.getSerialNr());
            in.setUnitId(ex.getUnitId());
            in.setAdded(ex.isAdded());
            in.setStockQuantity(ex.getStockQuantity());
        }
        return in;
    }

    @Override
    public MaterialEntity toExternalEntity(Material in) {
        MaterialEntity ex = new MaterialEntity();
        if (in != null) {
            ex.setCurrency(in.getCurrency());
            ex.setMaterialId(in.getMaterialId());
            ex.setMaterialNr(in.getMaterialNr());
            ex.setName(in.getName());
            ex.setPricePerUnit(in.getPricePerUnit());
            ex.setSerialNr(in.getSerialNr());
            ex.setUnitId(in.getUnitId());
            ex.setAdded(in.isAdded());
            ex.setStockQuantity(in.getStockQuantity());
        }
        return ex;
    }

    @Override
    public Class<Material> getInnerEntityClazz() {
        return Material.class;
    }

    @Override
    public Class<MaterialEntity> getExternalEntityClazz() {
        return MaterialEntity.class;
    }

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, MaterialEntity.ID, id);
    }

    public void queryForSearch(OrmLiteSqliteOpenHelper helper, String query) throws SQLException {
        Dao<MaterialEntity, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<MaterialEntity, ?> qb = dao.queryBuilder();
        qb.where().like(MaterialEntity.NAME, "%" + query + "%");
    }

    public void queryForAdded(OrmLiteSqliteOpenHelper helper) throws SQLException {
        Dao<MaterialEntity, ?> dao = helper.getDao(getExternalEntityClazz());
        QueryBuilder<MaterialEntity, ?> qb = dao.queryBuilder();
        qb.where().like(MaterialEntity.IS_ADDED, true);
        qb.orderByRaw(MaterialEntity.STOCK_ENTITY + " IS 0 ASC")
                .orderBy(MaterialEntity.NAME, true);
    }
}