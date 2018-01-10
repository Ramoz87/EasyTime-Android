package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class ContactView extends FrameLayout {

    @BindView(R.id.customer_name) TextView nameTextView;
    @BindView(R.id.customer_address) TextView addressTextView;

    public ContactView(Context context) {
        this(context, null);
    }

    public ContactView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_contact, this);
        ButterKnife.bind(this);
    }

    public void setCustomer(Contact contact, Address address) {
        if (contact != null && address != null) {
            nameTextView.setText(contact.getFullName());
            addressTextView.setText(address.getFullAddress());
        }
    }

    @OnClick(R.id.customer_call_button)
    public void onClickCall() {

    }

    @OnClick(R.id.customer_email_button)
    public void onClickMail() {

    }

    @OnClick(R.id.customer_map_button)
    public void onClickMap() {

    }
}
