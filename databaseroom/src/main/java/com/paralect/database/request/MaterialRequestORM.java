package com.paralect.database.request;

import com.example.paralect.easytime.model.Material;
import com.paralect.database.model.MaterialEntity;
import com.paralect.datasource.database.DatabaseRequestImpl;
import com.paralect.datasource.database.QueryBuilder;

import static com.example.paralect.easytime.model.Constants.IS_ADDED;
import static com.example.paralect.easytime.model.Constants.MATERIAL_ID;
import static com.example.paralect.easytime.model.Constants.NAME;
import static com.example.paralect.easytime.model.Constants.STOCK_ENTITY;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class MaterialRequestORM extends DatabaseRequestImpl<MaterialEntity, Material> {

    @Override
    public Material toAppEntity(MaterialEntity ex) {
        if (ex == null) return null;

        Material in = new Material();
        in.setCurrency(ex.getCurrency());
        in.setMaterialId(ex.getMaterialId());
        in.setMaterialNr(ex.getMaterialNr());
        in.setName(ex.getName());
        in.setPricePerUnit(ex.getPricePerUnit());
        in.setSerialNr(ex.getSerialNr());
        in.setUnitId(ex.getUnitId());
        in.setAdded(ex.isAdded());
        in.setStockQuantity(ex.getStockQuantity());
        return in;
    }

    @Override
    public MaterialEntity toDataSourceEntity(Material in) {
        if (in == null) return null;

        MaterialEntity ex = new MaterialEntity();
        ex.setCurrency(in.getCurrency());
        ex.setMaterialId(in.getMaterialId());
        ex.setMaterialNr(in.getMaterialNr());
        ex.setName(in.getName());
        ex.setPricePerUnit(in.getPricePerUnit());
        ex.setSerialNr(in.getSerialNr());
        ex.setUnitId(in.getUnitId());
        ex.setAdded(in.isAdded());
        ex.setStockQuantity(in.getStockQuantity());
        return ex;
    }

    @Override
    public Class<MaterialEntity> getDataSourceEntityClazz() {
        return MaterialEntity.class;
    }

    @Override
    public Class<Material> getAppEntityClazz() {
        return Material.class;
    }

    @Override
    public String getTableName() {
        return "materials";
    }

    public void queryForId(String id) throws Exception {
        queryWhere(MATERIAL_ID, id);
    }

    @Override
    public void queryForSearch(final String query) throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .select()
                .from(getTableName())
                .where(NAME)
                .like("%" + query + "%");
        setQueryBuilder(queryBuilder);
    }

    // SELECT * FROM `materials` WHERE `isAdded` LIKE 1 ORDER BY stockQuantity IS 0 ASC,`name`
    public void queryForAdded() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .select()
                .from(getTableName())
                .where(IS_ADDED).like(1)
                .orderBy(STOCK_ENTITY + " IS 0", NAME)
                .asc();
        setQueryBuilder(queryBuilder);
    }

    public void queryForResetMaterials() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();
        String[] fields = new String[]{IS_ADDED, STOCK_ENTITY};
        Object[] values = new Object[]{false, 0};
        queryBuilder.update(getTableName())
                .setValues(fields, values)
                .where(IS_ADDED).eq(true);
        setQueryBuilder(queryBuilder);
    }
}