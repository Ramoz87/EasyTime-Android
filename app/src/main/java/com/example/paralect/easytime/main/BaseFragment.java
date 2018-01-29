package com.example.paralect.easytime.main;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.paralect.easytime.views.KeypadEditorView;

/**
 * Created by alexei on 05.01.2018.
 */

// every fragment in MainActivity extends from this fragment
// as each of them has unique action bar

public abstract class BaseFragment extends Fragment {

    public static String FRAGMENT_KEY = "FRAGMENT_KEY";

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public void backForOneStep() {
        getMainActivity().backForOneStep();
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
        ActionBar actionBar = getMainActivity().getSupportActionBar();
        if (actionBar != null)
            onCreateActionBar(actionBar);
    }

    protected void showMainTopShadow(boolean show){
        getMainActivity().showMainTopShadow(show);
    }

    @Override
    public abstract void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    public abstract void onCreateActionBar(ActionBar actionBar);

    public abstract boolean needsOptionsMenu();

    public KeypadEditorView getKeypadEditor() {
        return getMainActivity().getKeypadEditor();
    }

    @Override
    public void onPause() {
        super.onPause();
        getKeypadEditor().collapse();
    }

    /**
     * @return false if fragment does not want to handle back press, true - if it does
     */
    public boolean onBackPressed() {
        return false;
    }

}
