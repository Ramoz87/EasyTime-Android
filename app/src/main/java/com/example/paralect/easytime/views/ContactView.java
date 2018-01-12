package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.utils.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class ContactView extends LinearLayout {

    @BindView(R.id.customer_name) TextView nameTextView;
    @BindView(R.id.customer_address) TextView addressTextView;

    private Contact mContact;
    private Address mAddress;

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
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
    }

    public void setCustomer(Contact contact, Address address) {
        mContact = contact;
        mAddress = address;
        if (mContact != null && mAddress != null) {
            nameTextView.setText(mContact.getFullName());
            addressTextView.setText(mAddress.getFullAddress());
        }
    }

    @OnClick(R.id.customer_call_button)
    public void onClickCall() {
        if (mContact != null)
            IntentUtils.phoneIntent(getContext(), mContact.getPhone());
    }

    @OnClick(R.id.customer_email_button)
    public void onClickMail() {
        if (mContact != null)
            IntentUtils.emailIntent(getContext(), mContact.getEmail());
    }

    @OnClick(R.id.customer_map_button)
    public void onClickMap() {
        if (mAddress != null) {
            IntentUtils.mapIntent(getContext(), mAddress.getQueryAddress());
        }
    }
}
