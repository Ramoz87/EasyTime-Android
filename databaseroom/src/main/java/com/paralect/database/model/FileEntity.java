package com.paralect.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.example.paralect.easytime.model.Constants.EXPENSE_ID;
import static com.example.paralect.easytime.model.Constants.FILE_ID;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.NAME;

/**
 * Created by alexei on 26.12.2017.
 */

@Entity(tableName = "files")
public class FileEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FILE_ID)
    private long fileId;

    @ColumnInfo(name = "fileUrl")
    private String fileUrl;

    @ColumnInfo(name = NAME)
    private String name;

    @ColumnInfo(name = EXPENSE_ID)
    private long expenseId;

    @ColumnInfo(name = JOB_ID)
    private String jobId;

    public FileEntity() {

    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFullFileUrl() {
        return "file://" + fileUrl;
    }

    public java.io.File getImageFile() {
        return new java.io.File(fileUrl);
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public boolean isSaved() {
        return getExpenseId() != 0;
    }
}
