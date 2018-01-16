package com.example.paralect.easytime.main.projects.project;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.views.SignatureView;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 15/01/2018.
 */

public class ProjectDetailFragment extends BaseFragment implements FloatingActionMenu.OnMenuToggleListener {

    private static final String TAG = ProjectDetailFragment.class.getSimpleName();

    public static final String ARG_JOB = "arg_job";

    @BindView(R.id.detail_title) TextView detailTitle;
    @BindView(R.id.activityList) EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.emptyListPlaceholder) View emptyListPlaceholder;
    @BindView(R.id.overlay) View overlay;
    @BindView(R.id.fam) FloatingActionMenu fam;

    private Animation fadeIn;
    private Animation fadeOut;

    private Job job;

    public static ProjectDetailFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_JOB, job);
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Job getJobArg() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_JOB))
            return args.getParcelable(ARG_JOB);
        else return null;
    }

    private void initJob() {
        if (job == null)
            job = getJobArg();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_detail, parent, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discount, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_discount) {

            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        initJob();
        String titleText = getResources().getString(R.string.project_number, job.getNumber());
        actionBar.setTitle(titleText);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
        initJob();
        initFam();
        initOverlay();
        initAnimations();

        populate();
    }

    private void populate() {
        initJob();
        Customer customer = job.getCustomer();
        detailTitle.setText(customer.getCompanyName());
    }

    private void initOverlay() {
        overlay.setVisibility(View.GONE);
        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hideOverlay();
                fam.close(true);
            }
        });
    }

    // adding overlay on front of app screen but under fam
    private void showOverlay() {
        fam.getMenuIconView().setImageResource(R.drawable.ic_close);
        fam.setMenuButtonColorNormalResId(R.color.dark_gray);
        overlay.startAnimation(fadeIn);
    }

    // removing overlay
    private void hideOverlay() {
        fam.getMenuIconView().setImageResource(R.drawable.ic_check);
        fam.setMenuButtonColorNormalResId(R.color.blue);
        overlay.startAnimation(fadeOut);
    }

    private void initFam() {
        Log.d(TAG, "initializing fam");
        Context context = getContext();
        Resources res = getResources();
        fam.setOnMenuToggleListener(this);
        fam.getMenuIconView().setImageResource(R.drawable.ic_check);
        fam.setIconAnimated(false);
    }

    private void initAnimations() {
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(AnimUtils.newAppearingAnimListener(overlay));
        fadeOut.setAnimationListener(AnimUtils.newDisappearingAnimListener(overlay));
    }

    // region Clicks
    @Override
    public void onMenuToggle(boolean opened) {
        if (opened) {
            showOverlay();
        } else {
            hideOverlay();
        }
    }

    @OnClick(R.id.detail_title)
    void onTitleClick() {

    }

    @OnClick(R.id.action_send)
    void onActionSendClick() {

    }

    @OnClick(R.id.action_save)
    void onActionSaveClick() {

    }

    @OnClick(R.id.action_sign)
    void onActionSignClick() {
        final SignatureDialogFragment signatureDialogFragment = SignatureDialogFragment.show(getActivity());
        signatureDialogFragment.setSignatureListener(new SignatureView.SignatureListener() {
            @Override
            public void onSigned(boolean signedByMe, byte[] signature) {
                signatureDialogFragment.dismiss();
            }

            @Override
            public void onCanceled() {
                signatureDialogFragment.dismiss();
            }
        });
    }
    // endregion

}