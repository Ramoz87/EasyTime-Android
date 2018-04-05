package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.JobWithAddress;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;
import com.example.paralect.easytime.model.ProjectType;
import com.example.paralect.easytime.model.Type;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.easytimedataormlite.request.AddressRequest;
import com.paralect.easytimedataormlite.request.BaseJobRequest;
import com.paralect.easytimedataormlite.request.CustomerRequestORM;
import com.paralect.easytimedataormlite.request.ObjectRequestORM;
import com.paralect.easytimedataormlite.request.OrderRequestORM;
import com.paralect.easytimedataormlite.request.ProjectRequestORM;
import com.paralect.easytimedataormlite.request.TypeRequestORM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 03/04/2018.
 */

public class JobSource extends EntitySource {

    public void updateJob(Job job) {
        try {
            @ProjectType.Type int projectType = job.getProjectType();
            if (projectType == ProjectType.Type.TYPE_OBJECT) {
                ObjectRequestORM request = new ObjectRequestORM();
                request.setEntity((Object) job);
                dataSource.update(request);
            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                ProjectRequestORM request = new ProjectRequestORM();
                request.setEntity((Project) job);
                dataSource.update(request);
            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                OrderRequestORM request = new OrderRequestORM();
                request.setEntity((Order) job);
                dataSource.update(request);
            }
        } catch (SQLException e) {
            Logger.e(TAG, e.getMessage());
        }
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        try {

            ObjectRequestORM objectRequest = new ObjectRequestORM();
            OrderRequestORM orderRequest = new OrderRequestORM();
            ProjectRequestORM projectRequest = new ProjectRequestORM();

            objectRequest.queryForList(null, null, null);
            orderRequest.queryForList(null, null, null);
            projectRequest.queryForList(null, null, null);

            List<Object> objects = dataSource.getList(objectRequest);
            List<Order> orders = dataSource.getList(orderRequest);
            List<Project> projects = dataSource.getList(projectRequest);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            CustomerRequestORM customerRequest = new CustomerRequestORM();
            for (Job job : jobs) {
                String customerId = job.getCustomerId();
                customerRequest.queryForId(customerId);
                Customer customer = dataSource.get(customerRequest);
                job.setCustomer(customer);
            }

            AddressRequest addressRequest = new AddressRequest();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    addressRequest.queryForId(jobWithAddress.getAddressId());
                    Address address = dataSource.get(addressRequest);
                    jobWithAddress.setAddress(address);
                }
            }

            TypeRequestORM typeRequest = new TypeRequestORM();
            for (Job job : jobs) {
                String statusId = job.getStatusId();
                typeRequest.queryForId(statusId);
                Type status = dataSource.get(typeRequest);
                job.setStatus(status);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return jobs;
    }

    public List<Object> getObjects(Customer customer) throws SQLException {
        ObjectRequestORM objectRequest = new ObjectRequestORM();
        return getJobs(objectRequest, customer, null, null);
    }

    public List<Order> getOrders(Customer customer) throws SQLException {
        OrderRequestORM orderRequest = new OrderRequestORM();
        return getJobs(orderRequest, customer, null, null);
    }

    public List<Project> getProjects(Customer customer) throws SQLException {
        ProjectRequestORM projectRequest = new ProjectRequestORM();
        return getJobs(projectRequest, customer, null, null);
    }

    public List<Integer> getJobTypes(Customer customer) {
        List<Integer> types = new ArrayList<>();
        try {
            String id = customer.getId();
            ObjectRequestORM objectRequest = new ObjectRequestORM();
            OrderRequestORM orderRequest = new OrderRequestORM();
            ProjectRequestORM projectRequest = new ProjectRequestORM();

            objectRequest.queryCountOfCustomers(id);
            orderRequest.queryCountOfCustomers(id);
            projectRequest.queryCountOfCustomers(id);

            if (dataSource.count(objectRequest) != 0)
                types.add(ProjectType.Type.TYPE_OBJECT);
            if (dataSource.count(orderRequest) != 0)
                types.add(ProjectType.Type.TYPE_ORDER);
            if (dataSource.count(projectRequest) != 0)
                types.add(ProjectType.Type.TYPE_PROJECT);

            return types;
        } catch (SQLException exc) {
            Logger.e(exc);
            return types;
        }
    }

    public long getJobCount(Customer customer, @ProjectType.Type int projectType) {
        try {

            String id = customer.getId();

            if (projectType == ProjectType.Type.TYPE_OBJECT) {
                ObjectRequestORM objectRequest = new ObjectRequestORM();
                objectRequest.queryCountOfCustomers(id);
                return dataSource.count(objectRequest);

            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                OrderRequestORM orderRequest = new OrderRequestORM();
                orderRequest.queryCountOfCustomers(id);
                return dataSource.count(orderRequest);

            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                ProjectRequestORM projectRequest = new ProjectRequestORM();
                projectRequest.queryCountOfCustomers(id);
                return dataSource.count(projectRequest);
            } else return 0L;

        } catch (SQLException exc) {
            Logger.e(exc);
            return 0L;
        }
    }

    public <T extends Job> List<T> getJobs(BaseJobRequest request, Customer customer, String query, String date) throws SQLException {

        String customerId = customer == null ? "" : customer.getId();
        request.queryForList(customerId, query, date);
        List<T> jobs = dataSource.getList(request);

        if (customer == null) {
            CustomerRequestORM customerRequest = new CustomerRequestORM();
            for (Job job : jobs) {
                customerId = job.getCustomerId();
                customerRequest.queryForId(customerId);
                customer = dataSource.get(customerRequest);
                job.setCustomer(customer);
            }
        }

        AddressRequest addressRequest = new AddressRequest();
        for (Job job : jobs) {
            if (job instanceof JobWithAddress) {
                JobWithAddress jobWithAddress = (JobWithAddress) job;
                addressRequest.queryForId(jobWithAddress.getAddressId());
                Address address = dataSource.get(addressRequest);
                jobWithAddress.setAddress(address);
            }
        }

        TypeRequestORM typeRequest = new TypeRequestORM();
        for (Job job : jobs) {
            typeRequest.queryForId(job.getStatusId());
            Type status = dataSource.get(typeRequest);
            job.setStatus(status);
        }
        return jobs;
    }

    public List<Job> getJobs(Customer customer, String query, String date) {
        List<Job> jobs = new ArrayList<>();
        try {

            String customerId = customer == null ? "" : customer.getId();

            ObjectRequestORM objectRequest = new ObjectRequestORM();
            OrderRequestORM orderRequest = new OrderRequestORM();
            ProjectRequestORM projectRequest = new ProjectRequestORM();

            objectRequest.queryForList(customerId, query, date);
            orderRequest.queryForList(customerId, query, date);
            projectRequest.queryForList(customerId, query, date);

            List<Object> objects = dataSource.getList(objectRequest);
            List<Order> orders = dataSource.getList(orderRequest);
            List<Project> projects = dataSource.getList(projectRequest);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            if (customer == null) {
                CustomerRequestORM customerRequest = new CustomerRequestORM();
                for (Job job : jobs) {
                    customerId = job.getCustomerId();
                    customerRequest.queryForId(customerId);
                    customer = dataSource.get(customerRequest);
                    job.setCustomer(customer);
                }
            }

            AddressRequest addressRequest = new AddressRequest();
            for (Job job : jobs) {
                if (job instanceof JobWithAddress) {
                    JobWithAddress jobWithAddress = (JobWithAddress) job;
                    addressRequest.queryForId(jobWithAddress.getAddressId());
                    Address address = dataSource.get(addressRequest);
                    jobWithAddress.setAddress(address);
                }
            }

            TypeRequestORM typeRequest = new TypeRequestORM();
            for (Job job : jobs) {
                typeRequest.queryForId(job.getStatusId());
                Type status = dataSource.get(typeRequest);
                job.setStatus(status);
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return jobs;
    }

    public List<Object> getObjects(String[] ids) {
        List<Object> objects = new ArrayList<>();
        try {
            ObjectRequestORM objectRequest = new ObjectRequestORM();
            AddressRequest addressRequest = new AddressRequest();
            CustomerRequestORM customerRequest = new CustomerRequestORM();
            TypeRequestORM typeRequest = new TypeRequestORM();

            if (ids != null) {
                for (String id : ids) {
                    objectRequest.queryForId(id);
                    Object o = dataSource.get(objectRequest);
                    if (o != null) {
                        addressRequest.queryForId(o.getAddressId());
                        Address address = dataSource.get(addressRequest);
                        o.setAddress(address);

                        customerRequest.queryForId(o.getCustomerId());
                        Customer customer = dataSource.get(customerRequest);
                        o.setCustomer(customer);

                        typeRequest.queryForId(o.getStatusId());
                        Type status = dataSource.get(typeRequest);
                        o.setStatus(status);

                        objects.add(o);
                    }
                }
            }
        } catch (SQLException exc) {
            Logger.e(exc);
        }
        return objects;
    }

}
