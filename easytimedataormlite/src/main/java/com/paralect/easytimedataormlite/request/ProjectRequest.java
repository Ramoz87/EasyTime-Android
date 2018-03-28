package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Project;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.ProjectEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ProjectRequest extends BaseJobRequest<Project, ProjectEntity> {

    @Override
    public Project toInternalEntity(ProjectEntity ex) {
        Project in = new Project();
        if (ex != null) {
            populateInternalEntity(in, ex);
            
            in.setDateEnd(ex.getDateEnd());
            in.setDateStart(ex.getDateStart());
            in.setObjectIds(ex.getObjectIds());
        }
        return in;
    }

    @Override
    public ProjectEntity toExternalEntity(Project in) {
        ProjectEntity ex = new ProjectEntity();
        if (in != null) {
            populateExternalEntity(in, ex);

            ex.setDateEnd(in.getDateEnd());
            ex.setDateStart(in.getDateStart());
            ex.setObjectIds(in.getObjectIds());
        }
        return ex;
    }

    @Override
    public Class<Project> getInnerEntityClazz() {
        return Project.class;
    }

    @Override
    public Class<ProjectEntity> getExternalEntityClazz() {
        return ProjectEntity.class;
    }
}
