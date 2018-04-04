package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Project;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.ProjectEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ProjectRequest extends BaseJobRequest<ProjectEntity, Project> {

    @Override
    public Project toAppEntity(ProjectEntity ex) {
        if (ex == null) return null;

        Project in = new Project();
        populateInternalEntity(in, ex);

        in.setDateEnd(ex.getDateEnd());
        in.setDateStart(ex.getDateStart());
        in.setObjectIds(ex.getObjectIds());
        return in;
    }

    @Override
    public ProjectEntity toDataSourceEntity(Project in) {
        if (in == null) return null;

        ProjectEntity ex = new ProjectEntity();
        populateExternalEntity(in, ex);

        ex.setDateEnd(in.getDateEnd());
        ex.setDateStart(in.getDateStart());
        ex.setObjectIds(in.getObjectIds());
        return ex;
    }

    @Override
    public Class<ProjectEntity> getDataSourceEntityClazz() {
        return ProjectEntity.class;
    }

    @Override
    public Class<Project> getAppEntityClazz() {
        return Project.class;
    }
}
