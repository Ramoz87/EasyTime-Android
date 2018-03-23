package com.paralect.easytimedataormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.paralect.datasource.core.Entity;

/**
 * Created by alexei on 26.12.2017.
 */

public class File implements Entity<Long> {

    @DatabaseField(columnName = "fileId", generatedId = true)
    private long fileId;

    @DatabaseField(columnName = "fileUrl")
    private String fileUrl;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "expenseId")
    private long expenseId;

    @DatabaseField(columnName = "jobId")
    private String jobId;

    public File() {

    }

    public Long getId() {
        return fileId;
    }

    public void setId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFullFileUrl() {
        return "file://" + fileUrl;
    }

    public java.io.File getImageFile(){
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

    public boolean isSaved(){
        return getId() != 0;
    }
}
