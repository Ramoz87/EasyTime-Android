package com.example.paralect.easytime.main.projects.project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.utils.anim.AnimUtils;

import java.util.ArrayList;
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
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 18.01.2018.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<Expense> expenses;
    private List<ViewHolder> viewHolders = new ArrayList<>();
    private boolean editorModeEnabled = false;

    public void setData(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    private Expense getItem(int position) {
        return expenses.get(position);
    }

    private void removeItem(int position) {
        expenses.remove(position);
        notifyItemRemoved(position);
    }

    public void toggle() {
        this.editorModeEnabled = !editorModeEnabled;
        for (ViewHolder vh : viewHolders) {
            vh.setEditorIconVisibility(editorModeEnabled);
        }
    }

    public void toggle(boolean editorModeEnabled) {
        if (editorModeEnabled == this.editorModeEnabled) return;
        toggle();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_expense, parent, false);
        ViewHolder holder = new ViewHolder(itemView, this);
        viewHolders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expense item = getItem(position);
        holder.bind(item, editorModeEnabled);
    }

    @Override
    public int getItemCount() {
        return expenses != null ? expenses.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expenseName)
        TextView expenseName;

        @BindView(R.id.expenseValue)
        TextView expenseValue;

        @BindView(R.id.delete)
        ImageView delete;

        @OnClick(R.id.delete)
        void delete() {
            asyncDelete(expense);
        }

        ActivityAdapter adapter;
        Expense expense;

        public ViewHolder(View itemView, ActivityAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        void bind(Expense expense, boolean editorModeEnabled) {
            this.expense = expense;
            expenseName.setText(expense.getName());
            expenseValue.setText(expense.getTypedValue());
            setEditorIconVisibility(editorModeEnabled);
        }

        void setEditorIconVisibility(boolean isVisible) {
            delete.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }

        private void asyncDelete(final Expense expense) {
            Observable<Expense> observable = Observable.create(new ObservableOnSubscribe<Expense>() {
                @Override
                public void subscribe(ObservableEmitter<Expense> emitter) throws Exception {
                    try {
                        if (!emitter.isDisposed()) {
                            EasyTimeManager.getInstance().deleteExpense(expense);
                            emitter.onNext(expense);
                            emitter.onComplete();
                        }

                    } catch (Throwable e) {
                        emitter.onError(e);
                    }
                }
            });

            Observer<Expense> observer = new Observer<Expense>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Expense expense) {
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
