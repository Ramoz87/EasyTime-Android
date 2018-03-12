package com.paralect.core;


import com.paralect.base.Model;

public final class Contact implements Model {
    private long id;
    private String phone;
    private String email;

    private Contact(long id, String phone, String email) {
        this.id = id;
        this.phone = phone;
        this.email = email;
    }

    public static Contact newContact(long id, String phone, String email) {
        return new Contact(id, phone, email);
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
