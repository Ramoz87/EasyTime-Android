package com.example.paralect.easytime.model;

/**
 * Created by Oleg Tarashkevich on 11/01/2018.
 */

import java.util.ArrayList;

public class JobsContainer {

    private ArrayList<Project> projects;
    private ArrayList<Order> orders;
    private ArrayList<Object> objects;

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }
}