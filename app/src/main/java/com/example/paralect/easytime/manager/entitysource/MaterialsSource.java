package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.database.request.MaterialRequestORM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class MaterialsSource extends EntitySource {

    public List<Material> getMaterials(String query) throws SQLException {
        MaterialRequestORM materialRequest = new MaterialRequestORM();
        materialRequest.queryForSearch(query);
        return dataSource.getList(materialRequest);
    }

    public void updateMaterial(Material material) {
        try {
            MaterialRequestORM materialRequest = new MaterialRequestORM();
            materialRequest.setEntity(material);
            dataSource.update(materialRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    public List<Material> getMyMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
            MaterialRequestORM materialRequest = new MaterialRequestORM();
            materialRequest.queryForAdded();
            List<Material> myMaterials = dataSource.getList(materialRequest);
            materials.addAll(myMaterials);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return materials;
    }

    public void deleteMyMaterials() {
        try {
            MaterialRequestORM materialRequest = new MaterialRequestORM();
            materialRequest.queryForResetMaterials();
            dataSource.update(materialRequest);
            Logger.d(TAG, "cleaned stock of my materials");
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

}
