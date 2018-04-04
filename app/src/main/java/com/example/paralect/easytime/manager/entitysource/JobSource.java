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
import com.paralect.easytimedataormlite.request.CustomerRequest;
import com.paralect.easytimedataormlite.request.ObjectRequest;
import com.paralect.easytimedataormlite.request.OrderRequest;
import com.paralect.easytimedataormlite.request.ProjectRequest;
import com.paralect.easytimedataormlite.request.TypeRequest;

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
                ObjectRequest request = new ObjectRequest();
                request.setEntity((Object) job);
                dataSource.update(request);
            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                ProjectRequest request = new ProjectRequest();
                request.setEntity((Project) job);
                dataSource.update(request);
            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                OrderRequest request = new OrderRequest();
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

            ObjectRequest objectRequest = new ObjectRequest();
            OrderRequest orderRequest = new OrderRequest();
            ProjectRequest projectRequest = new ProjectRequest();

            objectRequest.queryForList(null, null, null);
            orderRequest.queryForList(null, null, null);
            projectRequest.queryForList(null, null, null);

            List<Object> objects = dataSource.getList(objectRequest);
            List<Order> orders = dataSource.getList(orderRequest);
            List<Project> projects = dataSource.getList(projectRequest);

            jobs.addAll(objects);
            jobs.addAll(orders);
            jobs.addAll(projects);

            CustomerRequest customerRequest = new CustomerRequest();
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

            TypeRequest typeRequest = new TypeRequest();
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
        ObjectRequest objectRequest = new ObjectRequest();
        return getJobs(objectRequest, customer, null, null);
    }

    public List<Order> getOrders(Customer customer) throws SQLException {
        OrderRequest orderRequest = new OrderRequest();
        return getJobs(orderRequest, customer, null, null);
    }

    public List<Project> getProjects(Customer customer) throws SQLException {
        ProjectRequest projectRequest = new ProjectRequest();
        return getJobs(projectRequest, customer, null, null);
    }

    public List<Integer> getJobTypes(Customer customer) {
        List<Integer> types = new ArrayList<>();
        try {
            String id = customer.getId();
            ObjectRequest objectRequest = new ObjectRequest();
            OrderRequest orderRequest = new OrderRequest();
            ProjectRequest projectRequest = new ProjectRequest();

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
                ObjectRequest objectRequest = new ObjectRequest();
                objectRequest.queryCountOfCustomers(id);
                return dataSource.count(objectRequest);

            } else if (projectType == ProjectType.Type.TYPE_PROJECT) {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.queryCountOfCustomers(id);
                return dataSource.count(orderRequest);

            } else if (projectType == ProjectType.Type.TYPE_ORDER) {
                ProjectRequest projectRequest = new ProjectRequest();
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
            CustomerRequest customerRequest = new CustomerRequest();
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

        TypeRequest typeRequest = new TypeRequest();
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

            ObjectRequest objectRequest = new ObjectRequest();
            OrderRequest orderRequest = new OrderRequest();
            ProjectRequest projectRequest = new ProjectRequest();

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
                CustomerRequest customerRequest = new CustomerRequest();
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

            TypeRequest typeRequest = new TypeRequest();
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
            ObjectRequest objectRequest = new ObjectRequest();
            AddressRequest addressRequest = new AddressRequest();
            CustomerRequest customerRequest = new CustomerRequest();
            TypeRequest typeRequest = new TypeRequest();

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
