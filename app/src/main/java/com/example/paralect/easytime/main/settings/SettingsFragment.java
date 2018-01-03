package com.example.paralect.easytime.main.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.DatabaseHelper;
import com.example.paralect.easytime.model.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 29.12.2017.
 */

public class SettingsFragment extends Fragment {
    private static final String TAG = SettingsFragment.class.getSimpleName();

//    @BindView(R.id.helpLayout)
//    View helpLayout;
//
//    @BindView(R.id.sendFeedbackLayout)
//    View sendFeedbackLayout;
//
//    @BindView(R.id.logoutLayout)
//    View logoutLayout;

    @OnClick(R.id.helpLayout)
    void help() {

    }

    @OnClick(R.id.sendFeedbackLayout)
    void sendFeedback() {

    }

    @OnClick(R.id.logoutLayout)
    void logout() {
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_settings, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //
        DatabaseHelper helper = new DatabaseHelper(this.getContext().getApplicationContext());
        try {
            Dao<User, Long> userDao = helper.getUserDao();
            User user = userDao.queryForId(1L);
            Log.d(TAG, "user = " + user.getFirstName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
}
