package com.example.paralect.easytime.main.projects.project.invoice;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.paralect.easytime.main.CongratulationsActivity;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.main.projects.project.SignatureDialogFragment;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.TextUtil;
import com.example.paralect.easytime.utils.anim.AnimUtils;
import com.example.paralect.easytime.views.DiscountDialogView;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.SignatureView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.model.Constants.REQUEST_CODE_CONGRATULATIONS;

/**
 * Created by Oleg Tarashkevich on 15/01/2018.
 */

public class ProjectInvoiceFragment extends BaseFragment implements
        FloatingActionMenu.OnMenuToggleListener,
        IDataView<List<InvoiceCell>> {

    private static final String TAG = ProjectInvoiceFragment.class.getSimpleName();
    private static final String DATE_ARG = "date_arg";

    @BindView(R.id.detail_title) TextView detailTitle;
    @BindView(R.id.discount_title) TextView discountTitle;
    @BindView(R.id.activityList) EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.emptyListPlaceholder) View emptyListPlaceholder;
    @BindView(R.id.overlay) View overlay;
    @BindView(R.id.fam) FloatingActionMenu fam;
    @BindView(R.id.discount_dialog_view) DiscountDialogView discountDialogView;

    private ProjectInvoiceAdapter adapter = new ProjectInvoiceAdapter();
    private ProjectInvoicePresenter presenter = new ProjectInvoicePresenter();

    private Animation fadeIn;
    private Animation fadeOut;

    private Job job;
    private String date;
    private KeypadEditorView keypad;

    public static ProjectInvoiceFragment newInstance(@NonNull Job job, String date) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        args.putString(DATE_ARG, date);
        ProjectInvoiceFragment fragment = new ProjectInvoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(DATE_ARG))
            date = getArguments().getString(DATE_ARG);
        job = Job.fromBundle(bundle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_invoice, parent, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discount, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_discount) {
            showDialog();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
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

        initFam();
        initOverlay();
        initAnimations();
        initDialog();

        populate();
    }

    private void populate() {
        Customer customer = job.getCustomer();
        detailTitle.setText(customer.getCompanyName());

        int discount = job.getDiscount();
        if (discount != 0) {
            discountTitle.setText(getString(R.string.discount_value, String.valueOf(discount)));
            discountTitle.setVisibility(View.VISIBLE);
        }

        emptyRecyclerView.setEmptyView(emptyListPlaceholder);
        emptyRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        emptyRecyclerView.setLayoutManager(lm);

        presenter.setJob(job)
                .setDataView(this)
                .requestData(new String[]{"", date});
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
    void send(FloatingActionButton fab) {
        Intent intent = CongratulationsActivity.newIntent(getContext());
        MainActivity activity = getMainActivity();
        activity.startActivityForResult(intent, REQUEST_CODE_CONGRATULATIONS);
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

    @Override
    public void onDataReceived(List<InvoiceCell> expenses) {
        Log.d(TAG, String.format("received %s expenses", expenses.size()));
        adapter.setData(expenses);
    }

    // region Ugly dialog
    private void initDialog(){
        keypad = getKeypadEditor();
        discountDialogView.setVisibility(View.GONE);

        discountDialogView.getCanelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
        
        discountDialogView.getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
                applyDiscount();
            }
        });

        keypad.setupEditText(discountDialogView.geteditText());
        keypad.setOnCompletionListener(new KeypadEditorView.OnCompletionListener() {
            @Override
            public void onCompletion(KeypadEditorView editorView, String result) {
                hideDialog();
                applyDiscount();
            }
        });
    }

    private void showDialog() {

        if (!discountDialogView.isVisible()){

            keypad.showDoneButton();
            if (!keypad.isExpanded())
                keypad.expand();

            discountDialogView.geteditText().setText(null);
            discountDialogView.setVisibility(View.VISIBLE);
            showOverlay();
        }
    }

    private void hideDialog() {

        if (discountDialogView.isVisible()){

            if (keypad.isExpanded())
                keypad.collapse();

            discountDialogView.setVisibility(View.GONE);
            hideOverlay();
        }
    }

    private void applyDiscount(){
        String value = discountDialogView.geteditText().getText().toString();
        if (TextUtil.isNotEmpty(value)) {
            job.setDiscount(Integer.valueOf(value));
            Completable completable = Completable.fromCallable(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    EasyTimeManager.getInstance().updateJob(job);
                    return null;
                }
            });
            completable.subscribeOn(Schedulers.io()).subscribe();
            discountTitle.setText(getString(R.string.discount_value, value));
            discountTitle.setVisibility(View.VISIBLE);
        }
    }
    // endregion

}