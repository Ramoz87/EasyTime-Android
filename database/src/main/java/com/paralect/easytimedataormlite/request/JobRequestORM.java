package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Job;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.database.DatabaseRequest;
import com.paralect.easytimedataormlite.model.JobEntity;

/**
 * Created by Oleg Tarashkevich on 27/03/2018.
 */

public class JobRequestORM extends DatabaseRequest<JobEntity, Job, QueryBuilder<?, ?>> {

    @Override
    public Job toAppEntity(JobEntity ex) {
        if (ex == null) return null;
        Job in = new Job();
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
        return in;
    }

    @Override
    public JobEntity toDataSourceEntity(Job in) {
        if (in == null) return null;
        JobEntity ex = new JobEntity();
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
        return ex;
    }

    @Override
    public Class<JobEntity> getDataSourceEntityClazz() {
        return JobEntity.class;
    }

    @Override
    public Class<Job> getAppEntityClazz() {
        return Job.class;
    }
}
