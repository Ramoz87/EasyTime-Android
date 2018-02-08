package com.example.paralect.easytime.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.Address;
import com.example.paralect.easytime.model.Contact;
import com.example.paralect.easytime.utils.IntentUtils;
import com.example.paralect.easytime.utils.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class ContactView extends LinearLayout {

    @BindView(R.id.customer_name) TextView nameTextView;
    @BindView(R.id.customer_address) TextView addressTextView;
    @BindView(R.id.customer_call_button) ImageView callButton;
    @BindView(R.id.customer_email_button) ImageView emailButton;
    @BindView(R.id.customer_map_button) ImageView mapButton;

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
        int white = ContextCompat.getColor(getContext(), R.color.white);
        int disabled = ContextCompat.getColor(getContext(), R.color.disabled_icon);

        boolean hasPhone = TextUtil.isNotEmpty(contact.getPhone());
        Drawable d1 = setTint(callButton.getDrawable().getConstantState().newDrawable().mutate(), hasPhone ? white : disabled);
        callButton.setImageDrawable(d1);
        callButton.setEnabled(hasPhone);

        boolean hasEmail = TextUtil.isNotEmpty(contact.getEmail());
        Drawable d2 = setTint(emailButton.getDrawable().getConstantState().newDrawable().mutate(), hasEmail ? white : disabled);
        emailButton.setImageDrawable(d2);
        emailButton.setEnabled(hasEmail);

        boolean hasAddress = mAddress != null && mAddress.hasAnyAddress();
        if (hasAddress) {
            addressTextView.setText(mAddress.getFullAddressSpannable());
        }
        Drawable d3 = setTint(mapButton.getDrawable(), hasAddress ? white : disabled);
        mapButton.setImageDrawable(d3);
        mapButton.setEnabled(hasAddress);


        if (mContact != null) {
            nameTextView.setText(mContact.getFullName());
        } else {
            nameTextView.setText(null);
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

    public static Drawable setTint(Drawable drawable, int color) {
        final Drawable newDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }
}
