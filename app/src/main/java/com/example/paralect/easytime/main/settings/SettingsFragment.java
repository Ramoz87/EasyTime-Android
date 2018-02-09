package com.example.paralect.easytime.main.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paralect.easytime.login.LoginActivity;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.tutorial.TutorialActivity;
import com.example.paralect.easytime.manager.ETAccountManager;
import com.example.paralect.easytime.model.Constants;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.ViewUtils;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 29.12.2017.
 */

public class SettingsFragment extends BaseFragment {
    private static final String TAG = SettingsFragment.class.getSimpleName();

    @BindView(R.id.first_and_last_name)
    TextView firstAndLastName;

    @OnClick(R.id.helpLayout)
    void help() {
      startActivity(new Intent(getActivity(), TutorialActivity.class));
    }

    @OnClick(R.id.sendFeedbackLayout)
    void sendFeedback() {
        Log.d(TAG, "preparing Email Intent");
        MainActivity mainActivity = getMainActivity();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String[] emails = new String[] { "ramoz87@gmail.com" };
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, "EasyTime feedback");
        if (intent.resolveActivity(mainActivity.getPackageManager()) != null) {
            startActivityForResult(intent, Constants.REQUEST_CODE_SEND_FEEDBACK);
        }
    }

    @OnClick(R.id.logoutLayout)
    void logout() {
        ETAccountManager.getInstance().logout();
        Context context = getContext();
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
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
        init();
    }

    private void init() {
        User user = getUser();
        String text = user.getFirstName() + " " + user.getLastName();
        firstAndLastName.setText(text);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        ViewUtils.setTitle(getActivity(), R.string.nav_settings);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    private User getUser() {
        ETAccountManager accountManager = ETAccountManager.getInstance();
        return accountManager.getUser();
    }
}
