package com.example.paralect.easytime.main.projects.project;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Consumable;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.Material;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 18.01.2018.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<Consumable> consumables;

    public void setData(List<Consumable> consumables) {
        this.consumables = consumables;
        notifyDataSetChanged();
    }

    private Consumable getItem(int position) {
        return consumables.get(position);
    }

    private void removeItem(int position) {
        consumables.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_expense, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Consumable item = getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return consumables != null ? consumables.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expenseName)
        TextView expenseName;

        @BindView(R.id.expenseTime)
        TextView expenseTime;

        @BindView(R.id.delete)
        ImageView delete;

        @OnClick(R.id.delete)
        void delete() {
            asyncDelete(consumable);
        }

        ActivityAdapter adapter;
        Consumable consumable;

        public ViewHolder(View itemView, ActivityAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        void bind(Consumable consumable) {
            this.consumable = consumable;
            expenseName.setText(consumable.getName());
            //expenseTime.setVisibility(View.GONE);
            //delete.setVisibility(View.GONE);
        }

        private void asyncDelete(final Consumable consumable) {
            Observable<Consumable> observable = Observable.create(new ObservableOnSubscribe<Consumable>() {
                @Override
                public void subscribe(ObservableEmitter<Consumable> emitter) throws Exception {
                    try {
                        if (!emitter.isDisposed()) {
                            EasyTimeManager.getInstance().deleteConsumable(consumable);
                            emitter.onNext(consumable);
                            emitter.onComplete();
                        }

                    } catch (Throwable e) {
                        emitter.onError(e);
                    }
                }
            });

            Observer<Consumable> observer = new Observer<Consumable>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Consumable consumable) {
                    adapter.removeItem(getAdapterPosition());
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }
}
