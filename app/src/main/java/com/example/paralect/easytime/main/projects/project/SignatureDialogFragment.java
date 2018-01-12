package com.example.paralect.easytime.main.projects.project;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.IntentUtils;
import com.example.paralect.easytime.views.SignatureView;

/**
 * Created by Oleg Tarashkevich on 12/01/2018.
 */
public final class SignatureDialogFragment extends DialogFragment {

    public static final String TAG = "CampaignDialogFragment";
    private SignatureView mSignatureView;
    private SignatureView.SignatureListener mSignatureListener;

    public static SignatureDialogFragment show(Activity activity) {

        SignatureDialogFragment alarmDialogFragment = null;

        if (!IntentUtils.isFinishing(activity)) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            alarmDialogFragment = create(fragmentManager);
        }
        return alarmDialogFragment;
    }

    public static SignatureDialogFragment create(FragmentManager manager) {
        SignatureDialogFragment alarmDialogFragment = new SignatureDialogFragment();
        try {
            alarmDialogFragment.show(manager, TAG);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return alarmDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogAnimation);
        populateUI();
        builder.setView(mSignatureView);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private SignatureDialogFragment populateUI() {
        if (mSignatureView == null && getActivity() != null) {
            mSignatureView = new SignatureView(getActivity());
            mSignatureView.setSignatureListener(mSignatureListener);
        }
        return this;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    public void setSignatureListener(SignatureView.SignatureListener signatureListener) {
        mSignatureListener = signatureListener;
        if (mSignatureView != null)
            mSignatureView.setSignatureListener(signatureListener);
    }
}