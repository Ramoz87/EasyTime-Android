package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Project;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

public class ProjectsRequestCSV extends JobRequestCSV<Project> {

    public ProjectsRequestCSV() {
        setQuery("db/projects.csv");
    }

    @Override
    public Project toAppEntity(String[] fields) {
        Project project = null;
        try {
            project = new Project();
            fillJob(project, fields);

            project.setDateStart(fields[11]);
            project.setDateEnd(fields[12]);

            String objectIds = fields[13];
            objectIds = objectIds.replace("\"", "");
            String[] ids = objectIds.split(",[ ]*");
            if (ids.length == 1 && ids[0].isEmpty()) ids = new String[0];
            project.setObjectIds(ids);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public String[] toDataSourceEntity(Project type) {
        return new String[0];
    }

    @Override
    public Class<String[]> getDataSourceEntityClazz() {
        return String[].class;
    }

    @Override
    public Class<Project> getAppEntityClazz() {
        return Project.class;
    }

}
