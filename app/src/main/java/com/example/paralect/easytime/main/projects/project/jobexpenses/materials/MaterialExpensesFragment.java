package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.MetricsUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.views.KeypadEditorView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexei on 17.01.2018.
 */

public class MaterialExpensesFragment extends BaseFragment implements IMaterialExpenses<List<Material>>, MaterialExpensesAdapter.OnCheckedCountChangeListener, ExpandableLayout.OnExpansionUpdateListener {
    private static final String TAG = MaterialExpensesFragment.class.getSimpleName();

    public static final String ARG_KEYBOARD_STATE = "keyboard_state";

    @BindView(R.id.addMaterials) Button addMaterials;
    @BindView(R.id.list) EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.editor_layout) View editorLayout;
    private KeypadEditorView keypadEditorView;

    private Job job;
    private MaterialExpensesAdapter adapter = new MaterialExpensesAdapter();
    private MaterialExpensesPresenter presenter = new MaterialExpensesPresenter();

    public static MaterialExpensesFragment newInstance(@NonNull Job job) {
        Bundle args = new Bundle(1);
        args.putParcelable(Job.TAG, job);
        MaterialExpensesFragment fragment = new MaterialExpensesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initJob() {
        if (job == null)
            job = Job.fromBundle(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_expenses, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        keypadEditorView = getKeypadEditor();

        initJob();
        Resources res = getResources();
        addMaterials.setText(res.getString(R.string.add_materials, 0));

        adapter.setKeypadEditorView(keypadEditorView);
        adapter.setOnCheckedCountChangeListener(this);

        emptyRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        emptyRecyclerView.setLayoutManager(lm);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        decoration.setDrawable(drawable);
        emptyRecyclerView.addItemDecoration(decoration);

        restoreState();

        presenter.setDataView(this)
                .requestData(new String[]{job.getJobId()});
    }

    @Override
    public void onDestroyView() {
        saveState();
        super.onDestroyView();
    }

    private void saveState(){
        Bundle bundle = getArguments();
        if (bundle == null)
            bundle = new Bundle();
        bundle.putBoolean(ARG_KEYBOARD_STATE, keypadEditorView.isExpanded());
        setArguments(bundle);
    }

    private void restoreState(){
        Bundle bundle = getArguments();
        boolean show = bundle != null && bundle.getBoolean(ARG_KEYBOARD_STATE);
        if (show)
            showKeypad(false);
        else
            hideKeypad(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {

    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    private void showKeypad(boolean animate) {
        keypadEditorView.expand(animate);
    }

    private void hideKeypad(boolean animate) {
        keypadEditorView.collapse(animate);
    }

    @OnClick(R.id.addMaterials)
    void addMaterials() {
        List<MaterialExpense> materialExpenses = adapter.getCheckedMaterials();
        presenter.updateExpenses(job, materialExpenses, this);
    }

    @Override
    public void onCheckedCountChange(int totalCount) {
        String message = getResources().getString(R.string.add_materials, totalCount);
        addMaterials.setText(message);
    }

    // region IMaterialExpenses
    @Override
    public void onDataReceived(List<Material> materials) {
        adapter.setData(materials);
    }

    @Override
    public void onFinishing() {
        backForOneStep();
    }
    // endregion

    @Override
    public void onPause() {
        super.onPause();
        keypadEditorView.setOnExpansionUpdateListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        keypadEditorView.setOnExpansionUpdateListener(this);
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        Log.d(TAG, "expansion update: fraction = " + expansionFraction);
        int paddingTop = emptyRecyclerView.getPaddingTop();
        int paddingLeft = emptyRecyclerView.getPaddingLeft();
        int paddingRight = emptyRecyclerView.getPaddingRight();
        int buttonLayoutHeight = editorLayout.getMeasuredHeight();
        int navBarHeight = (int) MetricsUtils.convertDpToPixel(64);
        int paddingBottom = (int) (keypadEditorView.getMeasuredHeight() * expansionFraction);
        Log.d(TAG, String.format("Button layout height = %s, nav bar height = %s, keypad editor view = %s", buttonLayoutHeight, navBarHeight, paddingBottom));
        paddingBottom = paddingBottom - buttonLayoutHeight - navBarHeight;
        if (paddingBottom <= 0) {
            paddingBottom = 0;
        }
        Log.d(TAG, String.format("padding bottom = %s", paddingBottom));
        emptyRecyclerView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }
}
