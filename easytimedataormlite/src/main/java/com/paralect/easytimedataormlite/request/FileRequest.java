package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.File;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.easytimedataormlite.model.FileEntity;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class FileRequest extends ORMLiteRequest<FileEntity, File> {

    @Override
    public File toAppEntity(FileEntity ex) {
        if (ex == null) return null;
        File in = new File();
        in.setFileId(ex.getFileId());
        in.setFileUrl(ex.getFileUrl());
        in.setName(ex.getName());
        in.setExpenseId(ex.getExpenseId());
        in.setJobId(ex.getJobId());
        return in;
    }

    @Override
    public FileEntity toDataSourceEntity(File in) {
        if (in == null) return null;
        FileEntity ex = new FileEntity();
        ex.setFileId(in.getFileId());
        ex.setFileUrl(in.getFileUrl());
        ex.setName(in.getName());
        ex.setExpenseId(in.getExpenseId());
        ex.setJobId(in.getJobId());
        return ex;
    }

    @Override
    public Class<FileEntity> getDataSourceEntityClazz() {
        return FileEntity.class;
    }

    @Override
    public Class<File> getAppEntityClazz() {
        return File.class;
    }

    public void queryForId(OrmLiteSqliteOpenHelper helper, String id) throws SQLException {
        queryWhere(helper, FileEntity.ID, id);
    }

    public void queryForLast(OrmLiteSqliteOpenHelper helper) throws SQLException {
        queryForLast(helper, FileEntity.ID);
    }

    public void queryForFirst(OrmLiteSqliteOpenHelper helper) throws SQLException {
        queryForFirst(helper, FileEntity.ID);
    }
}