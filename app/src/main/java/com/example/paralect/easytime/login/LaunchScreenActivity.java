package com.example.paralect.easytime.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.paralect.easytime.BuildConfig;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.ETPreferenceManager;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.JobWithAddress;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.CalendarUtils;
import com.example.paralect.easytime.utils.FakeCreator;
import com.example.paralect.easytime.utils.Logger;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.EasyTimeApplication.TAG;

/**
 * Created by Oleg Tarashkevich on 29.01.2018.
 */

public class LaunchScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        init();
    }

   private void init(){
       Completable completable = Completable.fromCallable(new Callable<Void>() {
           @Override
           public Void call() throws Exception {
               setupDataBase();
               return null;
           }
       });

       completable
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Action() {
                   @Override
                   public void run() throws Exception {
                       Intent intent = new Intent(LaunchScreenActivity.this, LoginActivity.class);
                       startActivity(intent);
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) {
                       Logger.d(TAG, throwable.getMessage());
                       Logger.e(throwable);
                   }
               });
   }

    private void setupDataBase(){
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        preferenceManager.plusLaunch();
        if (BuildConfig.DEBUG && preferenceManager.isLaunchFirst()) { // pre-populate data from assets
            Log.d(TAG, "filling data from db");
            FakeCreator fakeCreator = getDefaultFakeCreator();
            fillData(fakeCreator);
        } else {
            List<Job> jobs = EasyTimeManager.getInstance().getAllJobs();
            for (Job job : jobs) {
                // Log.d(TAG, "date for job: " + job.getDate());
            }
        }

        updateMyMaterials();
    }

    private void fillData(FakeCreator fakeCreator) {
        final String userCSVPath = "db/users.csv";
        final String typeCSVPath = "db/types.csv";
        final String customerCSVPath = "db/customers.csv";
        final String materialCSVPath = "db/materials.csv";
        final String objectCSVPath = "db/objects.csv";
        final String orderCSVPath = "db/orders.csv";
        final String projectCSVPath = "db/projects.csv";

        fillData(fakeCreator, userCSVPath, User.class);
        fillData(fakeCreator, typeCSVPath, Type.class);
        fillData(fakeCreator, customerCSVPath, Customer.class);
        fillData(fakeCreator, materialCSVPath, Material.class);
        fillData(fakeCreator, objectCSVPath, Object.class);
        fillData(fakeCreator, orderCSVPath, Order.class);
        fillData(fakeCreator, projectCSVPath, Project.class);
    }


    private <E> void fillData(FakeCreator fakeCreator, String csvPath, Class<E> clazz) {
        try {
            List<E> items = fakeCreator.parse(csvPath, clazz);
            Dao<E, String> dao = EasyTimeManager.getInstance().getHelper().getDao(clazz);
            Log.d(TAG, String.format("===// %s //===", clazz.getSimpleName()));

            Dao<Address, Long> addressDao = EasyTimeManager.getInstance().getHelper().getAddressDao();
            for (E item : items) {
                Log.d(TAG, item.toString());

                if (item instanceof JobWithAddress) {
                    JobWithAddress job = (JobWithAddress) item;
                    Address address = job.getAddress();
                    addressDao.create(address);
                    job.setAddressId(address.getAddressId());
                }

                if (item instanceof Customer) {
                    Dao<Contact, Long> contactDao = EasyTimeManager.getInstance().getHelper().getContactDao();
                    Customer customer = (Customer) item;
                    List<Contact> contacts = customer.getContacts();
                    for (Contact contact : contacts) {
                        Log.d(TAG, "Contact: " + contact);
                        contactDao.create(contact);
                    }
                    Address address = customer.getAddress();
                    addressDao.create(address);
                    customer.setAddressId(address.getAddressId());
                }
                dao.createOrUpdate(item);
            }
            Log.d(TAG, "filled " + clazz.getSimpleName() + " class");
        } catch (IOException | SQLException e) {
            Logger.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private FakeCreator getDefaultFakeCreator() {
        return new FakeCreator(getAssets()) {
            @Override
            public <E> java.lang.Object create(Class<E> clazz, String[] fields) {
                if (clazz.equals(User.class)) {
                    return createUser(fields);
                } else if (clazz.equals(Customer.class)) {
                    return createCustomer(fields);
                } else if (clazz.equals(Type.class)) {
                    return createType(fields);
                } else if (clazz.equals(Material.class)) {
                    return createMaterial(fields);
                } else if (clazz.equals(Object.class)) {
                    return createObject(fields);
                } else if (clazz.equals(Order.class)) {
                    return createOrder(fields);
                } else if (clazz.equals(Project.class)) {
                    return createProject(fields);
                }
                return null;
            }

            User createUser(String[] fields) {
                User user = new User();
                user.setUserId(fields[0]);
                user.setFirstName(fields[1]);
                user.setLastName(fields[2]);
                user.setUserName(fields[3]);
                user.setPassword(fields[4]);
                return user;
            }

            Type createType(String[] fields) {
                Type type = new Type();
                type.setTypeId(fields[0]);
                type.setType(fields[1]);
                type.setName(fields[2]);
                Log.d(TAG, "type: " + type);
                return type;
            }

            Customer createCustomer(String[] fields) {
                Customer customer = new Customer();
                customer.setCustomerId(fields[21]);
                customer.setCompanyName(fields[38]);
                customer.setFirstName(fields[53]);
                customer.setLastName(fields[38]);

                Contact c = new Contact();
                c.setCustomerId(customer.getCustomerId());
                c.setFirstName(customer.getFirstName());
                c.setLastName(customer.getLastName());
                c.setEmail(fields[23]);
                c.setFax(fields[25]);
                c.setPhone(fields[47]);

                Contact c1 = new Contact();
                c1.setCustomerId(customer.getCustomerId());
                c1.setFirstName(fields[57]);
                c1.setLastName(fields[58]);
                c1.setEmail(getValidString(fields[55]));
                c1.setPhone(getValidString(fields[54]));

                Contact c2 = new Contact();
                c2.setCustomerId(customer.getCustomerId());
                c2.setFirstName(fields[62]);
                c2.setLastName(fields[63]);
                c2.setEmail(getValidString(fields[60]));
                c2.setPhone(getValidString(fields[59]));

                Address address = new Address();
                address.setCity(fields[39]);
                address.setStreet(fields[15]);
                address.setZip(fields[41]);

                List<Contact> contacts = new ArrayList<>();
                contacts.add(c);
                contacts.add(c1);
                contacts.add(c2);
                customer.setContacts(contacts);
                customer.setAddress(address);
                return customer;
            }

            Material createMaterial(String[] fields) {
                Material material = new Material();
                material.setMaterialId(fields[0]);
                material.setCurrency(fields[1]);
                material.setMaterialNr(Integer.valueOf(fields[2]));
                material.setName(fields[3]);
                material.setPricePerUnit(Integer.valueOf(fields[4]));
                material.setSerialNr(Long.valueOf(fields[5]));
                material.setUnitId(fields[6]);
                return material;
            }

            Object createObject(String[] fields) {
                Object object = new Object();
                fillJob(object, fields);

                Address address = new Address();
                address.setStreet(fields[16]);
                address.setCity(fields[17]);
                address.setZip(fields[18]);
                object.setAddress(address);
                return object;
            }

            Order createOrder(String[] fields) {
                Order order = new Order();
                fillJob(order, fields);

                order.setContact(fields[14]);
                order.setDeliveryTime(fields[15]);

                Address address = new Address();
                address.setStreet(fields[16]);
                address.setCity(fields[17]);
                address.setZip(fields[18]);
                order.setAddress(address);

                String objectIds = fields[13];
                objectIds = objectIds.replace("\"", "");
                String[] ids = objectIds.split(",[ ]*");
                if (ids.length == 1 && ids[0].isEmpty()) ids = new String[0];
                order.setObjectIds(ids);
                return order;
            }

            Project createProject(String[] fields) {
                Project project = new Project();
                fillJob(project, fields);

                project.setDateStart(fields[11]);
                project.setDateEnd(fields[12]);

                String objectIds = fields[13];
                objectIds = objectIds.replace("\"", "");
                String[] ids = objectIds.split(",[ ]*");
                if (ids.length == 1 && ids[0].isEmpty()) ids = new String[0];
                project.setObjectIds(ids);

                return project;
            }

            void fillJob(Job job, String[] fields) {
                job.setEntityType(fields[0]);
                job.setJobId(fields[1]);
                job.setCustomerId(fields[2]);
                job.setStatusId(fields[3]);
                job.setTypeId(fields[4]);
                job.setNumber(Integer.valueOf(fields[5]));
                job.setName(fields[6]);
                job.setInformation(fields[7]);

                String memberIds = fields[8];
                memberIds = memberIds.replace("\"", "");
                String[] ids = memberIds.split(",[ ]*");
                if (ids.length == 1 && ids[0].isEmpty()) ids = new String[0];
                job.setMemberIds(ids);
                // fields[8]?
                job.setCurrency(fields[10]);

                // random date
                Date date = CalendarUtils.nextDate();
//                Date date = new Date();
//                String dateString = CalendarUtils.stringFromDate(date, CalendarUtils.SHORT_DATE_FORMAT);
//                Log.d(TAG, "new date for job: " + dateString);
                job.setDate(date.getTime());
            }

            private String getValidString(String s) {
                if (s.trim().isEmpty()) return ""; // s contains only whitespaces
                else return s;
            }
        };
    }

    private void updateMyMaterials() {
        ETPreferenceManager preferenceManager = ETPreferenceManager.getInstance(this);
        boolean firstLaunchInDay = preferenceManager.isCurrentLaunchFirstInDay();
        if (firstLaunchInDay) {
            Log.d(TAG, "First launch in a day, cleaning stock of materials");
            EasyTimeManager.getInstance().deleteMyMaterials();
        } else {
            Log.d(TAG, "not a first launch in a day");
        }
        preferenceManager.saveCurrentLaunchDate();
    }
}
