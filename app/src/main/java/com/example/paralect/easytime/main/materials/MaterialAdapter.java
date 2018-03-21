package com.example.paralect.easytime.main.materials;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.utils.TextUtil;
import com.example.paralect.easytime.utils.anim.AnimUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.model.ExpenseUnit.Type.MATERIAL;

/**
 * Created by alexei on 15.01.2018.
 */

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {
    private static final String TAG = MaterialAdapter.class.getSimpleName();

    private List<Material> materials;
    private List<ViewHolder> viewHolders = new ArrayList<>();
    private MaterialEditingListener mMaterialEditingListener;
    private boolean primaryState = true;

    public MaterialAdapter() {

    }

    public void setData(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }

    public void toggle(boolean primaryState) {
        if (this.primaryState == primaryState) return;
        toggle();
    }

    public void toggle() {
        this.primaryState = !primaryState;
        for (ViewHolder holder : viewHolders) {
            holder.transform(primaryState);
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
        ViewHolder holder = new ViewHolder(view, this, mMaterialEditingListener);
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

        private static Animation incDec = null;

        private Material material;
        private MaterialAdapter adapter;
        private MaterialEditingListener mMaterialEditingListener;
        private final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (TextUtil.isEmpty(text) || text.equalsIgnoreCase("0")) {
                    Log.d(TAG, String.format("text changed = %s", text));
                    text = "1";
                    count.setText(text);
                } else {
                    int value = Integer.parseInt(text);
                    material.setStockQuantity(value);
                    asyncUpdate(material, newUpdateObserver());

                }

                int length = text.length();
                count.setSelection(length);
            }
        };

        private DisposableObserver<Material> newUpdateObserver() {
            return new DisposableObserver<Material>() {
                @Override
                public void onNext(Material material) {

                }

                @Override
                public void onError(Throwable e) {
                    Logger.e(e);
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
        @BindView(R.id.material_unit) TextView unit;
        @BindView(R.id.materialCount) EditText count;
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

        @OnTouch(R.id.materialCount)
        boolean onCountTouch(View v, MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                v.requestFocus();
                count.setSelection(0, count.getText().toString().length());
                if (mMaterialEditingListener != null)
                    mMaterialEditingListener.onItemEditingStarted(count);
                return true;
            }
            return true;
        }

        @OnClick(R.id.plus)
        void plus(View view) {
            view.startAnimation(incDec);
            String text = this.count.getText().toString();
            int number = Integer.valueOf(text);
            number++;
            count.setText(String.valueOf(number));
        }

        @OnClick(R.id.minus)
        void minus(View view) {
            view.startAnimation(incDec);
            String text = this.count.getText().toString();
            int number = Integer.valueOf(text);
            if (number <= 1) return;
            number--;
            count.setText(String.valueOf(number));
        }

        public ViewHolder(View itemView, MaterialAdapter adapter, MaterialEditingListener materialEditingListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
            if (incDec == null) {
                incDec = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.inc_dec_press);
            }
            mMaterialEditingListener = materialEditingListener;
        }

        void bind(final Material material) {
            boolean primaryState = adapter.primaryState;
            minus.setVisibility(primaryState ? View.VISIBLE : View.GONE);
            plus.setImageResource(primaryState ? R.drawable.ic_plus_material : R.drawable.ic_trash);
            plus.setOnClickListener(primaryState ? plusHandler : remover);

            this.material = material;
            name.setText(material.getName());
            String unitName = EasyTimeManager.getInstance().getUnitName(MATERIAL, material);
            unit.setText(unitName);
            Resources res = itemView.getResources();
            number.setText(res.getString(R.string.material_number, material.getMaterialNr()));

            count.removeTextChangedListener(textWatcher);
            count.setText(String.valueOf(material.getStockQuantity()));
            count.addTextChangedListener(textWatcher);
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
            final int halfDur = 100;
            if (primaryState) {
                // minus.setVisibility(View.VISIBLE);
                AnimUtils.showWithAnimation(minus, halfDur, halfDur);
                AnimUtils.performReincarnation(plus, R.drawable.ic_plus_material, halfDur, halfDur, 0);
                // plus.setImageResource(R.drawable.ic_plus_material);
                plus.setOnClickListener(plusHandler);

            } else {
                // minus.setVisibility(View.GONE);
                AnimUtils.hideWithAnimation(minus, halfDur, 0, false);
                // plus.setImageResource(R.drawable.ic_trash);
                AnimUtils.performReincarnation(plus, R.drawable.ic_trash, halfDur, halfDur, 0);
                plus.setOnClickListener(remover);
            }
        }
    }

    public void setMaterialEditingListener(MaterialEditingListener materialEditingListener) {
        mMaterialEditingListener = materialEditingListener;
    }

    public interface MaterialEditingListener {
        void onItemEditingStarted(EditText editText);
    }
}
