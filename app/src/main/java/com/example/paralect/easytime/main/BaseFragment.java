package com.example.paralect.easytime.main;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/**
 * Created by alexei on 05.01.2018.
 */

// every fragment in MainActivity extends from this fragment
// as each of them has unique action bar

public abstract class BaseFragment extends Fragment {

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(needsOptionsMenu());
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onCreateActionBar(getMainActivity().getSupportActionBar());
    }

    @Override
    public abstract void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    public abstract void onCreateActionBar(ActionBar actionBar);

    public abstract boolean needsOptionsMenu();
}
