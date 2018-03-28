package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Project;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.ProjectEntity;

/**
 * Created by Oleg Tarashkevich on 28/03/2018.
 */

public class ProjectRequest extends EntityRequestImpl<Project, ProjectEntity, QueryBuilder<?, ?>> {

    @Override
    public Project toInnerEntity(ProjectEntity ex) {
        Project in = new Project();
        if (ex != null) {
            in.setCurrency(ex.getCurrency());
            in.setCustomerId(ex.getCustomerId());
            in.setDate(ex.getDate());
            in.setEntityType(ex.getEntityType());
            in.setInformation(ex.getInformation());
            in.setId(ex.getId());
            in.setName(ex.getName());
            in.setNumber(ex.getNumber());
            in.setStatusId(ex.getStatusId());
            in.setTypeId(ex.getTypeId());
            in.setDiscount(ex.getDiscount());
            in.setMemberIds(ex.getMemberIds());
            
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
            ex.setCurrency(in.getCurrency());
            ex.setCustomerId(in.getCustomerId());
            ex.setDate(in.getDate());
            ex.setEntityType(in.getEntityType());
            ex.setInformation(in.getInformation());
            ex.setId(in.getId());
            ex.setName(in.getName());
            ex.setNumber(in.getNumber());
            ex.setStatusId(in.getStatusId());
            ex.setTypeId(in.getTypeId());
            ex.setDiscount(in.getDiscount());
            ex.setMemberIds(in.getMemberIds());

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
