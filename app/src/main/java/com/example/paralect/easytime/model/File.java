package com.example.paralect.easytime.model;

import com.j256.ormlite.field.DatabaseField;
import com.paralect.datasource.core.Entity;

import java.util.ArrayList;
import java.util.List;

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

    public static List<File> mockList(){
        String[] pictures = new String[]{
                "https://cdn.pixabay.com/photo/2016/09/01/10/23/image-1635747_960_720.jpg",
                "https://cdn.pixabay.com/photo/2014/10/06/16/25/screen-476405_960_720.jpg",
                "https://cdn.pixabay.com/photo/2015/02/15/03/04/japanese-umbrellas-636870_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/04/04/08/53/art-2200731_960_720.jpg"
        };
        List<File> files = new ArrayList<>();
        for (int i = 0; i < pictures.length; i++){
            File file = new File();
            file.setId((long) i);
            file.setFileUrl(pictures[i]);
            file.setName("Name_" + i);
            files.add(file);
        }
        return files;
    }
}
