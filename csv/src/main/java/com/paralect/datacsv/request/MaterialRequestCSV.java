package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Type;
import com.paralect.datasource.core.EntityRequestImpl;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class MaterialRequestCSV extends EntityRequestImpl<String[], Material, Object> {

    public MaterialRequestCSV() {
        setQuery("db/materials.csv");
    }

    @Override
    public Material toAppEntity(String[] fields) {
        Material material = new Material();
        material.setMaterialId(fields[0]);
        material.setCurrency(fields[1]);
        material.setMaterialNr(Integer.valueOf(fields[2]));
        material.setName(fields[3]);
        material.setPricePerUnit(Integer.valueOf(fields[4]));
        material.setSerialNr(Long.valueOf(fields[5]));
        material.setUnitId(fields[6]);
        return material;
    }

    @Override
    public String[] toDataSourceEntity(Material type) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

    @Override
    public Class<Material> getAppEntityClazz() {
        return Material.class;
    }

}
