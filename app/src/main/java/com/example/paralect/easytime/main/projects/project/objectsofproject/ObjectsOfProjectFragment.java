package com.example.paralect.easytime.main.projects.project.objectsofproject;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.projects.project.jobexpenses.expenses.ExpensesFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.materials.MaterialExpensesFragment;
import com.example.paralect.easytime.main.projects.project.jobexpenses.time.WorkTypeFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.ObjectCollection;
import com.example.paralect.easytime.utils.TextUtil;

import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 17.01.2018.
 */

public class ObjectsOfProjectFragment extends AbsStickyFragment implements IDataView<SortedMap<Character, List<Object>>> {

    public static final String TAG = ObjectsOfProjectFragment.class.getSimpleName();
    public static final String ARG_OBJECT_COLLECTION = "arg_project";

    private ObjectsOfProjectPresenter presenter = new ObjectsOfProjectPresenter();
    private ObjectsOfProjectAdapter adapter = new ObjectsOfProjectAdapter();
    private ObjectCollection objectCollection;

    public static ObjectsOfProjectFragment newInstance(@NonNull ObjectCollection objectCollection, @NonNull String fragmentName) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_OBJECT_COLLECTION, objectCollection);
        args.putString(FRAGMENT_KEY, fragmentName);
        ObjectsOfProjectFragment fragment = new ObjectsOfProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ObjectCollection getObjectCollectionArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_OBJECT_COLLECTION)) {
            return args.getParcelable(ARG_OBJECT_COLLECTION);
        } else return null;
    }

    private String getFragmentName() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(FRAGMENT_KEY)) {
            return args.getString(FRAGMENT_KEY);
        } else return null;
    }

    private void initProject() {
        if (objectCollection == null) {
            objectCollection = getObjectCollectionArg();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProject();
        presenter.setDataView(this)
                .requestData(objectCollection.getObjectIds());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discount, menu);

        Button button = new Button(getContext());
        button.setText(R.string.expenses_skip);
        button.setTextColor(Color.WHITE);
        button.setAllCaps(false);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_ripple_white);
        button.setBackground(drawable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragment((Job) objectCollection);
            }
        });

        MenuItem item = menu.findItem(R.id.item_discount);
        item.setActionView(button);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.objects);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        return adapter;
    }

    @Override
    public void onDataReceived(SortedMap<Character, List<Object>> characterListSortedMap) {
        adapter.setData(characterListSortedMap);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object object = adapter.getItem(i);
        pushFragment(object);
    }

    private void pushFragment(Job job){
        String fragmentName = getFragmentName();

        if (TextUtil.isNotEmpty(fragmentName)) {
            Fragment fragment = null;

            if (fragmentName.equalsIgnoreCase(WorkTypeFragment.TAG))
                fragment = WorkTypeFragment.newInstance(job);

            else if (fragmentName.equalsIgnoreCase(ExpensesFragment.TAG))
                fragment = ExpensesFragment.newInstance(job);

            else if (fragmentName.equalsIgnoreCase(MaterialExpensesFragment.TAG))
                fragment = MaterialExpensesFragment.newInstance(job);

            if (fragment != null)
                getMainActivity().getFragmentNavigator().pushFragment(fragment);
        }
    }
}
