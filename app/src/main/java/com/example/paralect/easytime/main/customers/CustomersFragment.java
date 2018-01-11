package com.example.paralect.easytime.main.customers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.FragmentNavigator;
import com.example.paralect.easytime.main.customers.customer.CustomerFragment;
import com.example.paralect.easytime.model.Customer;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 26.12.2017.
 */

public class CustomersFragment extends AbsStickyFragment implements IDataView<SortedMap<Character, List<Customer>>> {

    private final CustomersPresenter presenter = new CustomersPresenter();
    private final CustomerStickyAdapter adapter = new CustomerStickyAdapter();
    private FragmentNavigator navigator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (FragmentNavigator) context;
    }

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // setHasOptionsMenu(true);
        return super.onCreateView(inflater, parent, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        presenter.setDataView(this)
                .setupQuerySearch((SearchView) menu.findItem(R.id.item_search).getActionView())
                .requestData(new String[] {""});
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.nav_clients);
        // ViewUtils.setTitle(getActivity(), R.string.nav_clients);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Customer customer = adapter.getItem(i);
        CustomerFragment fragment = CustomerFragment.newInstance(customer);
        navigator.pushFragment(fragment);
    }

    @Override
    public void onDataReceived(SortedMap<Character, List<Customer>> map) {
        adapter.setData(map);
    }
}
