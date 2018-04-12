package com.paralect.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;

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
import com.paralect.datasource.room.RoomDataSource;

import java.util.Map;

import static com.example.paralect.easytime.model.Constants.DATABASE_NAME;
import static com.example.paralect.easytime.model.Constants.DATABASE_VERSION;

/**
 * Created by alexei on 03.01.2018.
 */
@Database(entities = {UserEntity.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class DatabaseHelper extends RoomDataSource {

    public abstract Daos.AddressDao getAddressDao();

    public abstract Daos.ContactDao getContactDao();

    public abstract Daos.CustomerDao getCustomerDao();

    public abstract Daos.ExpenseDao getExpenseDao();

    public abstract Daos.FileDao getFileDao();

    public abstract Daos.MaterialDao getMaterialDao();

    public abstract Daos.ObjectDao getObjectDao();

    public abstract Daos.OrderDao getOrderDao();

    public abstract Daos.ProjectDao getProjectDao();

    public abstract Daos.TypeDao getTypeDao();

    public abstract Daos.UserDao getUserDao();

    public static DatabaseHelper getInstance(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                DatabaseHelper.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Override
    protected void setupDao(Map<Class, EntityRoomDao> daoMap) {
        daoMap.put(AddressEntity.class, getAddressDao());
        daoMap.put(ContactEntity.class, getContactDao());
        daoMap.put(CustomerEntity.class, getCustomerDao());
        daoMap.put(ExpenseEntity.class, getExpenseDao());
        daoMap.put(FileEntity.class, getFileDao());
        daoMap.put(MaterialEntity.class, getMaterialDao());
        daoMap.put(ObjectEntity.class, getObjectDao());
        daoMap.put(OrderEntity.class, getOrderDao());
        daoMap.put(ProjectEntity.class, getProjectDao());
        daoMap.put(TypeEntity.class, getTypeDao());
        daoMap.put(UserEntity.class, getUserDao());
    }
}
