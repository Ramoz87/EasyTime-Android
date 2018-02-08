package com.example.paralect.easytime.main.materials;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.paralect.easytime.main.BaseFragment;
import com.example.paralect.easytime.main.IDataView;
import com.example.paralect.easytime.main.materials.chooser.MaterialChooserFragment;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.MetricsUtils;
import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.views.KeypadEditorView;
import com.example.paralect.easytime.views.KeypadView;
import com.github.clans.fab.FloatingActionMenu;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 26.12.2017.
 */

public class StockFragment extends BaseFragment
        implements IDataView<List<Material>>,MaterialAdapter.MaterialEditingListener, ExpandableLayout.OnExpansionUpdateListener, View.OnClickListener {

    private static final String TAG = StockFragment.class.getSimpleName();

    private MaterialsPresenter presenter = new MaterialsPresenter();
    private MaterialAdapter adapter = new MaterialAdapter();

    @BindView(R.id.list) EmptyRecyclerView list;
    @BindView(R.id.placeholder) View placeholder;
    private KeypadEditorView keypad;
    private FloatingActionMenu fam;

    private MenuItem deleteMaterials;

    void openMaterialChooser() {
        Log.d(TAG, "on fab click");
        Fragment chooser = MaterialChooserFragment.newInstance();
        getMainActivity().getFragmentNavigator().pushFragment(chooser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static StockFragment newInstance() {
        return new StockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock, parent, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "on view created");
        ButterKnife.bind(this, view);
        initFam();
        init();
        updateMyMaterials();
    }

    private void init() {
        Context context = getContext();
        keypad = getKeypadEditor();
        adapter.setMaterialEditingListener(this);
        final RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
            private void update() {
                if (deleteMaterials != null) {
                    Log.d(TAG, "updating menu item visibility");
                    deleteMaterials.setVisible(adapter.getItemCount() != 0);
                }
            }

            @Override
            public void onChanged() {
                super.onChanged();
                update();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                update();
            }
        };
        adapter.registerAdapterDataObserver(observer);
        list.setEmptyView(placeholder);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.divider);
        decoration.setDrawable(drawable);
        list.addItemDecoration(decoration);
    }

    private void initFam() {
        fam = getFam();
        fam.setOnMenuButtonClickListener(this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_materials, menu);
        deleteMaterials = menu.findItem(R.id.delete_materials);
    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle(R.string.nav_stock);
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }

    @Override
    public boolean needsFam() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "on options item selected");
        if (item.getItemId() == R.id.delete_materials) {
            adapter.toggle();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReceived(List<Material> materials) {
        int count = materials.size();
        Log.d(TAG, String.format("received %s material%s", count, count == 0 ? "" : "s"));
        adapter.setData(materials);
    }

    private void updateMyMaterials() {
        presenter.setDataView(this)
                .requestData(null);
    }

    @Override
    public void onItemEditingStarted(EditText editText) {
        if (!keypad.isExpanded()){
            keypad.expand();
        }
        keypad.setOnKeypadItemClickListener(new KeypadHandler(editText));
        adapter.toggle(true);
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        Log.d(TAG, "expansion update: fraction = " + expansionFraction);
        int minPaddingBottom = (int) MetricsUtils.convertDpToPixel(80);
        int paddingTop = list.getPaddingTop();
        int paddingLeft = list.getPaddingLeft();
        int paddingRight = list.getPaddingRight();
        int paddingBottom = (int) (keypad.getMeasuredHeight() * expansionFraction);
        if (paddingBottom < minPaddingBottom) paddingBottom = minPaddingBottom; // min bottom padding
        list.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    public void onClick(View view) {
        openMaterialChooser();
    }

    /**
     * Added this copy of class to avoid text selection function in the {@link KeypadEditorView.KeypadHandler#onDeleteClick}
     */
    private class KeypadHandler implements KeypadView.OnKeypadItemClickListener {

        private EditText editText;

        private KeypadHandler(@NonNull EditText editText) {
            this.editText = editText;
        }

        @Override
        public void onNextClick() {
            keypad.toggle();
        }

        @Override
        public void onDeleteClick() {
            if (editText != null) {
                String text = editText.getText().toString();
                int pos1 = editText.getSelectionStart();
                int pos2 = editText.getSelectionEnd();

                if (pos1 == 0 && pos2 == 0) { // cursor is at th beginning, nothing to delete
                    return;
                }

                if (pos2 == pos1) {
                    pos1--;
                }

                int length = text.length();
                String result = text.substring(0, pos1)
                        + text.substring(pos2, length < 0 ? 0 :length);
                editText.setText(result);
            }
        }

        @Override
        public void onNumberClick(int number) {
            if (editText != null) {
                String text = editText.getText().toString();
                int pos1 = editText.getSelectionStart();
                int pos2 = editText.getSelectionEnd();
                int length = text.length();

                String result = text.substring(0, pos1)
                        + String.valueOf(number)
                        + text.substring(pos2, length < 0 ? 0 :length);
                editText.setText(result);
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        if (keypad.isExpanded()) {
            keypad.collapse();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "on pause");
        keypad.setOnExpansionUpdateListener(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMainActivity().resetFamSettings();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");
        keypad.setOnExpansionUpdateListener(this);
        adapter.toggle(true);
    }
}
