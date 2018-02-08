package com.example.paralect.easytime.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.paralect.easytime.views.KeypadEditorView;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by alexei on 05.01.2018.
 */

// every fragment in MainActivity extends from this fragment
// as each of them has unique action bar

public abstract class BaseFragment extends Fragment {

    public static String FRAGMENT_KEY = "FRAGMENT_KEY";
    public static final String ARG_KEYBOARD_STATE = "keyboard_state";

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
        handleFam();
    }

    private void handleFam() {
        Boolean flag = needsFam();
        if (flag == null) {
            // just ignore
        } else if (flag) {
            getFam().setVisibility(View.VISIBLE);
        } else {
            getFam().setVisibility(View.GONE);
        }
    }

    protected void showMainTopShadow(boolean show){
        getMainActivity().showMainTopShadow(show);
    }

    @Override
    public abstract void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    public abstract void onCreateActionBar(ActionBar actionBar);

    public abstract boolean needsOptionsMenu();

    /**
     * @return true if fragment needs visible fam
     * false if fragment needs to hide fam
     * null if fragment does not want to handle fam visibility
     */
    public Boolean needsFam() {
        return false;
    }

    public FloatingActionMenu getFam() {
        return getMainActivity().getFam();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getKeypadEditor().collapse();
        // getMainActivity().hideOverlay(false);
        // getFam().close(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        handleFam();
    }

    /**
     * @return false if fragment does not want to handle back press, true - if it does
     */
    public boolean onBackPressed() {
        return false;
    }

    protected void invalidateOptionsMenu() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.invalidateOptionsMenu();
        }
    }

    // region Keyboard
    public KeypadEditorView getKeypadEditor() {
        return getMainActivity().getKeypadEditor();
    }

    protected void saveState() {
        Bundle bundle = getArguments();
        if (bundle == null)
            bundle = new Bundle();
        bundle.putBoolean(ARG_KEYBOARD_STATE, getKeypadEditor().isExpanded());
        setArguments(bundle);
    }

    protected void restoreState() {
        Bundle bundle = getArguments();
        boolean show = bundle != null && bundle.getBoolean(ARG_KEYBOARD_STATE);
        if (show)
            showKeypad(false);
        else
            hideKeypad(false);
    }

    private void showKeypad(boolean animate) {
        getKeypadEditor().expand(animate);
    }

    private void hideKeypad(boolean animate) {
        getKeypadEditor().collapse(animate);
    }
    // endregion

}
