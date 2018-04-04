package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.Job;
import com.paralect.easytimedataormlite.request.FileRequest;

import java.sql.SQLException;
import java.util.List;

import static com.example.paralect.easytime.model.Constants.JOB_ID;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class FileSource extends EntitySource {

    public List<File> getFiles(Job job) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.queryWhere(JOB_ID, job.getId());
        return dataSource.getList(fileRequest);
    }

    public void saveFile(File file) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.setEntity(file);
        dataSource.saveOrUpdate(fileRequest);
    }

    public File saveFileAndGet(File file) throws SQLException {
        saveFile(file);
        // retrieve
        FileRequest fileRequest = new FileRequest();
        fileRequest.queryForLast();
        return dataSource.get(fileRequest);
    }

    public void deleteFile(File file) throws SQLException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.setEntity(file);
        dataSource.delete(fileRequest);
    }
}
