package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.paralect.easytime.model.Constants.EXPENSE_ID;
import static com.example.paralect.easytime.model.Constants.FILE_ID;
import static com.example.paralect.easytime.model.Constants.JOB_ID;
import static com.example.paralect.easytime.model.Constants.NAME;

/**
 * Created by alexei on 26.12.2017.
 */

@DatabaseTable(tableName = "files")
public class FileEntity {

    @DatabaseField(columnName = FILE_ID, generatedId = true)
    private long fileId;

    @DatabaseField(columnName = "fileUrl")
    private String fileUrl;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = EXPENSE_ID)
    private long expenseId;

    @DatabaseField(columnName = JOB_ID)
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
