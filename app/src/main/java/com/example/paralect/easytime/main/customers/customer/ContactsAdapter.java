package com.example.paralect.easytime.main.customers.customer;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.views.ContactView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class ContactsAdapter extends PagerAdapter {

    private List<Contact> mContacts = new ArrayList<>();
    private Address mAddress;

    ContactsAdapter(List<Contact> contacts, Address address) {
        mContacts = contacts;
        mAddress = address;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        Contact contact = mContacts.get(position);
        ContactView view = new ContactView(container.getContext());
        view.setCustomer(contact, mAddress);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}
