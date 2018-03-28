package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Job;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.EntityRequestImpl;
import com.paralect.easytimedataormlite.model.JobEntity;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public class JobRequest extends EntityRequestImpl<Job, JobEntity, QueryBuilder<?, ?>> {

    @Override
    public Job toInnerEntity(JobEntity ex) {
        Job in = new Job();
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
        }
        return in;
    }

    @Override
    public JobEntity toExternalEntity(Job in) {
        JobEntity ex = new JobEntity();
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
        }
        return ex;
    }

    @Override
    public Class<Job> getInnerEntityClazz() {
        return Job.class;
    }

    @Override
    public Class<JobEntity> getExternalEntityClazz() {
        return JobEntity.class;
    }
}
