package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.easytimedataormlite.request.MaterialRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class MaterialsSource extends EntitySource {

    public List<Material> getMaterials(String query) throws SQLException {
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.queryForSearch(query);
        return dataSource.getList(materialRequest);
    }

    public void updateMaterial(Material material) {
        try {
            MaterialRequest materialRequest = new MaterialRequest();
            materialRequest.setEntity(material);
            dataSource.update(materialRequest);
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

    public List<Material> getMyMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
            MaterialRequest materialRequest = new MaterialRequest();
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
            MaterialRequest materialRequest = new MaterialRequest();
            materialRequest.queryForResetMaterials();
            dataSource.update(materialRequest);
            Logger.d(TAG, "cleaned stock of my materials");
        } catch (SQLException exc) {
            Logger.e(exc);
        }
    }

}
