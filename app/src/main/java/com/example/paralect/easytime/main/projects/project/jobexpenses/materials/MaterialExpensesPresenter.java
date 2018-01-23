package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;

import com.example.paralect.easytime.main.materials.MaterialsPresenter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.Logger;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexei on 17.01.2018.
 */

class MaterialExpensesPresenter extends MaterialsPresenter {

    void updateExpenses(final Job job, final List<MaterialExpense> materialExpenses, final IMaterialExpenses view) {

        Flowable<List<MaterialExpense>> flowable = Flowable.create(new FlowableOnSubscribe<List<MaterialExpense>>() {
            @Override
            public void subscribe(FlowableEmitter<List<MaterialExpense>> emitter) throws Exception {
                try {
                    if (!emitter.isCancelled()) {
                        EasyTimeManager manager = EasyTimeManager.getInstance();
                        for (MaterialExpense expense : materialExpenses) {
                            if (expense.isAdded) {
                                manager.saveExpense(job.getJobId(), expense.material, expense.count);
                            }
                        }
                        emitter.onNext(materialExpenses);
                        emitter.onComplete();
                    }
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.LATEST);

        flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MaterialExpense>>() {
                    @Override
                    public void accept(List<MaterialExpense> materialExpenses) throws Exception {
                      if (view != null)
                          view.onFinishing();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(throwable);
                    }
                });
    }
}
