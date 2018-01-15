package com.example.paralect.easytime.main.materials;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.model.Material;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 15.01.2018.
 */

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {

    private List<Material> materials;

    public MaterialAdapter() {

    }

    public void setData(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_material, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Material item = getItem(position);
        holder.bind(item);
    }

    public Material getItem(int position) {
        return materials.get(position);
    }

    @Override
    public int getItemCount() {
        return materials != null ? materials.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Material material;

        @BindView(R.id.materialName) TextView name;
        @BindView(R.id.materialNumber) TextView number;
        @BindView(R.id.materialCount) TextView count;
        @OnClick(R.id.plus)
        void plus(View view) {
            int result = changeCount(1);
            material.setCount(result);
            asyncUpdate(material);
        }

        @OnClick(R.id.minus)
        void minus(View view) {
            int result = changeCount(-1);
            material.setCount(result);
            asyncUpdate(material);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Material material) {
            this.material = material;
            name.setText(material.getName());
            Resources res = itemView.getResources();
            number.setText(res.getString(R.string.material_number, material.getMaterialNr()));
            count.setText(String.valueOf(material.getCount()));
        }

        private int changeCount(int diff) {
            String text = this.count.getText().toString();
            int count = Integer.valueOf(text);
            count += diff;
            if (count < 0) count = 0;
            return count;
        }

        private void asyncUpdate(final Material material) {
            Observable<Material> observable = Observable.create(new ObservableOnSubscribe<Material>() {
                @Override
                public void subscribe(ObservableEmitter<Material> emitter) throws Exception {
                    try {
                        if (!emitter.isDisposed()) {
                            EasyTimeManager.getInstance().updateMaterial(material);
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
                    .subscribe(new DisposableObserver<Material>() {
                        @Override
                        public void onNext(Material material) {
                            count.setText(String.valueOf(material.getCount()));
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
