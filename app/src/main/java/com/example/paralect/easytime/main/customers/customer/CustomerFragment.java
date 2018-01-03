package com.example.paralect.easytime.main.customers.customer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.Order;
import com.example.paralect.easytime.model.Project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerFragment extends Fragment {
    private static final String TAG = CustomerFragment.class.getSimpleName();

    public static final String ARG_PROJECT_LIST = "arg_project_list";
    public static final String ARG_ORDER_LIST = "arg_order_list";
    public static final String ARG_OBJECT_LIST = "arg_objects_list";

    @BindView(R.id.actionContainer)
    LinearLayout actionContainer;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private Customer customer;

    public static CustomerFragment newInstance() {
        return new CustomerFragment();
    }

    public static CustomerFragment newInstance(ArrayList<Project> projects, ArrayList<Order> orders, ArrayList<Object> objects) {
        Bundle args = new Bundle(3);
        args.putParcelableArrayList(ARG_PROJECT_LIST, projects);
        args.putParcelableArrayList(ARG_ORDER_LIST, orders);
        args.putParcelableArrayList(ARG_OBJECT_LIST, objects);
        CustomerFragment fragment = new CustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private ArrayList<Project> getProjects() {
        Bundle args = getArguments();
        if (args.containsKey(ARG_PROJECT_LIST))
            return args.getParcelableArrayList(ARG_PROJECT_LIST);
        else return new ArrayList<>();
    }

    private ArrayList<Order> getOrders() {
        Bundle args = getArguments();
        if (args.containsKey(ARG_ORDER_LIST))
            return args.getParcelableArrayList(ARG_ORDER_LIST);
        else return new ArrayList<>();
    }

    private ArrayList<Object> getObjects() {
        Bundle args = getArguments();
        if (args.containsKey(ARG_OBJECT_LIST))
            return args.getParcelableArrayList(ARG_OBJECT_LIST);
        else return new ArrayList<>();
    }

    private void init() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setToolbarElevation(0);
        // FragmentManager fm = activity.getSupportFragmentManager();
        FragmentManager fm = getChildFragmentManager();
        JobSectionPagerAdapter adapter = new JobSectionPagerAdapter(getContext(), fm, getProjects(), getOrders(), getObjects());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        addNewContactAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performCall();
            }
        }, R.drawable.ic_phone, R.string.call);
        addNewContactAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        }, R.drawable.ic_message, R.string.email);
        addNewContactAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOnMap();
            }
        }, R.drawable.ic_checkpoint, R.string.map);
    }

    private void addNewContactAction(View.OnClickListener listener, @DrawableRes int drawableResId, @StringRes int stringResId) {
        Log.d(TAG, "adding new action");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View actionView = inflater.inflate(R.layout.include_contact_action, actionContainer, false);
        ((ImageView) actionView.findViewById(R.id.actionIcon)).setImageResource(drawableResId);
        ((TextView) actionView.findViewById(R.id.actionName)).setText(stringResId);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) actionView.getLayoutParams();
        lp.setMargins(25, 0, 25, 0);
        actionView.setLayoutParams(lp); // need ?
        actionView.setOnClickListener(listener);
        actionContainer.addView(actionView);
    }

    private void performCall() {

    }

    private void sendMail() {

    }

    private void showOnMap() {

    }
}
