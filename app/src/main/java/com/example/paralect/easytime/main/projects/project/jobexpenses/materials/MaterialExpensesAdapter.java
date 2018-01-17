package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.views.KeypadEditorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 17.01.2018.
 */

public class MaterialExpensesAdapter extends RecyclerView.Adapter<MaterialExpensesAdapter.ViewHolder> {
    private static final String TAG = MaterialExpensesAdapter.class.getSimpleName();

    private Job job;
    private List<Material> materials;
    private KeypadEditorView keypadEditorView;

    public void setJob(Job job) {
        this.job = job;
    }

    public void setKeypadEditorView(KeypadEditorView editorView) {
        this.keypadEditorView = editorView;
    }

    public void setData(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }

    private Material getItem(int position) {
        return materials.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_expense, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Material item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return materials != null ? materials.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements KeypadEditorView.OnCompletionListener {

        @BindView(R.id.materialName) TextView materialName;
        @BindView(R.id.materialNumber) TextView materialNumber;
        @BindView(R.id.switcher) ViewSwitcher switcher;
        @BindView(R.id.materialCount) TextView materialCount;
        @BindView(R.id.materialCountEditor) EditText materialCountEditor;

        @OnFocusChange(R.id.materialCountEditor)
        void onFocusChange(View view, boolean b) {
            Log.d(TAG, "switcher changed focus to " + b);
            if (!b) switcher.showPrevious();
        }

        @OnClick(R.id.materialCount)
        void onMaterialCountClick(TextView materialCount) {
            Log.d(TAG, "on material count click");
            KeypadEditorView editorView = adapter.keypadEditorView;
            if (editorView != null) {
                editorView.setupEditText(materialCountEditor);
                editorView.setOnCompletionListener(this);
                switcher.showNext();
                materialCountEditor.requestFocus();
                editorView.expand(true);
            }
        }

        MaterialExpensesAdapter adapter;
        Material material;
        Resources res;

        public ViewHolder(View itemView, MaterialExpensesAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            res = itemView.getResources();
            this.adapter = adapter;

            materialCountEditor.setRawInputType(InputType.TYPE_CLASS_TEXT);
            materialCountEditor.setTextIsSelectable(true);
            materialCountEditor.requestFocus();
        }

        void bind(Material material) {
            this.material = material;
            materialName.setText(material.getName());
            materialNumber.setText(res.getString(R.string.material_number, material.getMaterialNr()));
            materialCount.setText(String.valueOf(material.getCount()));
        }

        @Override
        public void onCompletion(KeypadEditorView keypadEditorView, String result) {
            Log.d(TAG, "on completion");
            keypadEditorView.collapse(true);
            Job job = adapter.job;
            if (job == null) return;

            int value;
            if (result.isEmpty()) value = 0;
            else value = Integer.valueOf(result);

            asyncCreateOrUpdateExpense(job.getJobId(), material, value);
        }

        private void asyncCreateOrUpdateExpense(final String jobId, final Material material, final int value) {
            Observable<Material> observable = Observable.create(new ObservableOnSubscribe<Material>() {
                @Override
                public void subscribe(ObservableEmitter<Material> emitter) throws Exception {
                    try {
                        if (!emitter.isDisposed()) {
                            EasyTimeManager.getInstance().saveExpense(jobId, material, value);
                            material.setCount(value);
                            emitter.onNext(material);
                            emitter.onComplete();
                        }

                    } catch (Throwable e) {
                        emitter.onError(e);
                    }
                }
            });

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Material>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Material material) {
                            Log.d(TAG, "successfully");
                            //switcher.showPrevious();
                            materialCountEditor.clearFocus();
                            materialCount.setText(String.valueOf(material.getCount()));
                            // Toast.makeText(itemView.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error");
                            // Toast.makeText(itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
