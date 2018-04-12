package com.paralect.database;

import android.arch.persistence.room.Dao;

import com.paralect.database.model.AddressEntity;
import com.paralect.database.model.ContactEntity;
import com.paralect.database.model.CustomerEntity;
import com.paralect.database.model.ExpenseEntity;
import com.paralect.database.model.FileEntity;
import com.paralect.database.model.MaterialEntity;
import com.paralect.database.model.ObjectEntity;
import com.paralect.database.model.OrderEntity;
import com.paralect.database.model.ProjectEntity;
import com.paralect.database.model.TypeEntity;
import com.paralect.database.model.UserEntity;
import com.paralect.datasource.room.EntityRoomDao;

/**
 * Created by Oleg Tarashkevich on 10/04/2018.
 */

public class Daos {

    @Dao
    public interface AddressDao extends EntityRoomDao<AddressEntity> {
    }

    @Dao
    public interface ContactDao extends EntityRoomDao<ContactEntity> {
    }

    @Dao
    public interface CustomerDao extends EntityRoomDao<CustomerEntity> {
    }

    @Dao
    public interface ExpenseDao extends EntityRoomDao<ExpenseEntity> {
    }

    @Dao
    public interface FileDao extends EntityRoomDao<FileEntity> {
    }

    @Dao
    public interface MaterialDao extends EntityRoomDao<MaterialEntity> {
    }

    @Dao
    public interface ObjectDao extends EntityRoomDao<ObjectEntity> {
    }

    @Dao
    public interface OrderDao extends EntityRoomDao<OrderEntity> {
    }

    @Dao
    public interface ProjectDao extends EntityRoomDao<ProjectEntity> {
    }

    @Dao
    public interface TypeDao extends EntityRoomDao<TypeEntity> {
    }

    @Dao
    public interface UserDao extends EntityRoomDao<UserEntity> {
    }

}
