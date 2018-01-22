package com.example.paralect.easytime.main.materials;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Material;

import java.util.ArrayList;
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
    private List<ViewHolder> viewHolders = new ArrayList<>();

    public MaterialAdapter() {

    }

    public void setData(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }

    public void transform(boolean primaryStage) {
        for (ViewHolder holder : viewHolders) {
            holder.transform(primaryStage);
        }
    }

    private void removeItem(int position) {
        materials.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_material, parent, false);
        ViewHolder holder = new ViewHolder(view, this);
        viewHolders.add(holder);
        return holder;
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
        private MaterialAdapter adapter;

        private static Animation incDec = null;

        private DisposableObserver<Material> newUpdateObserver() {
            return new DisposableObserver<Material>() {
                @Override
                public void onNext(Material material) {
                    count.setText(String.valueOf(material.getStockQuantity()));
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            };
        }

        private DisposableObserver<Material> newRemoveObserver() {
            return new DisposableObserver<Material>() {
                @Override
                public void onNext(Material material) {
                    int pos = getAdapterPosition();
                    adapter.removeItem(pos);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            };
        }

        @BindView(R.id.materialName) TextView name;
        @BindView(R.id.materialNumber) TextView number;
        @BindView(R.id.materialCount) TextView count;
        @BindView(R.id.plus) ImageView plus;
        @BindView(R.id.minus) ImageView minus;

        private final View.OnClickListener remover = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(incDec);
                material.setAdded(false);
                material.setStockQuantity(0);
                asyncUpdate(material, newRemoveObserver());
            }
        };

        private final View.OnClickListener plusHandler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus(view);
            }
        };

        @OnClick(R.id.plus)
        void plus(View view) {
            view.startAnimation(incDec);
            int result = changeCount(1);
            material.setStockQuantity(result);
            asyncUpdate(material, newUpdateObserver());
        }

        @OnClick(R.id.minus)
        void minus(View view) {
            view.startAnimation(incDec);
            int result = changeCount(-1);
            material.setStockQuantity(result);
            asyncUpdate(material, newUpdateObserver());
        }

        public ViewHolder(View itemView, MaterialAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
            if (incDec == null) {
                incDec = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.inc_dec_press);
            }
        }

        void bind(Material material) {
            this.material = material;
            name.setText(material.getName());
            Resources res = itemView.getResources();
            number.setText(res.getString(R.string.material_number, material.getMaterialNr()));
            count.setText(String.valueOf(material.getStockQuantity()));
        }

        private int changeCount(int diff) {
            String text = this.count.getText().toString();
            int count = Integer.valueOf(text);
            count += diff;
            if (count < 0) count = 0;
            return count;
        }

        private void asyncUpdate(final Material material, DisposableObserver<Material> observer) {
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
                    .subscribe(observer);
        }

        void transform(boolean primaryState) {
            if (primaryState) {
                minus.setVisibility(View.VISIBLE);
                plus.setImageResource(R.drawable.ic_plus_material);
                plus.setOnClickListener(plusHandler);

            } else {
                minus.setVisibility(View.GONE);
                plus.setImageResource(R.drawable.ic_trash);
                plus.setOnClickListener(remover);
            }
        }
    }
}
