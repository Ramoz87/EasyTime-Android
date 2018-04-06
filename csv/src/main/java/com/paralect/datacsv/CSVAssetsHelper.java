package com.paralect.datacsv;

import android.content.res.AssetManager;

import com.opencsv.CSVReader;
import com.paralect.datasource.core.DataSource;
import com.paralect.datasource.core.EntityRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class CSVAssetsHelper implements DataSource<Object> {

    private AssetManager assetManager;

    public CSVAssetsHelper(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public <DS, AP> AP get(EntityRequest<DS, AP, Object> request) throws Throwable {
        return null;
    }

    @Override
    public <DS, AP> List<AP> getList(EntityRequest<DS, AP, Object> request) throws IOException {
        List<AP> items = new ArrayList<>();
        InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(request.getQuery()));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        CSVReader reader = new CSVReader(bufferedReader);
        String [] lines;
        int index = 0;
        while ((lines = reader.readNext()) != null) {
            if (index++ == 0) continue;
            AP item = request.toAppEntity((DS)lines);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public <DS, AP> void save(EntityRequest<DS, AP, Object> request) throws Throwable {

    }

    @Override
    public <DS, AP> void saveOrUpdate(EntityRequest<DS, AP, Object> request) throws Throwable {

    }

    @Override
    public <DS, AP> void update(EntityRequest<DS, AP, Object> request) throws Throwable {

    }

    @Override
    public <DS, AP> void delete(EntityRequest<DS, AP, Object> request) throws Throwable {

    }

    @Override
    public <DS, AP> long count(EntityRequest<DS, AP, Object> request) throws Throwable {
        return 0;
    }
}
