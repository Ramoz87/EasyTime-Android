package com.example.paralect.easytime.main.customers;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.AlphabetStickyAdapter;
import com.example.paralect.easytime.manager.entitysource.JobSource;
import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.ProjectType;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 27.12.2017.
 */

public class CustomerStickyAdapter extends AlphabetStickyAdapter<Customer> implements StickyListHeadersAdapter {

    @Override
    public long getItemId(int i) {
        return getItem(i).getCompanyName().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Customer item = getItem(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_customer, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();
        vh.bind(item);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.companyName)
        TextView companyName;

        @BindView(R.id.jobs)
        TextView jobs;

        Resources res;
        private final JobSource jobSource = new JobSource();

        ViewHolder(View v) {
            res = v.getResources();
            ButterKnife.bind(this, v);
        }

        void bind(Customer customer) {
            companyName.setText(customer.getCompanyName());
            asyncLoadInfo(customer);
        }

        void asyncLoadInfo(final Customer customer) {
            Observable<long[]> observable = Observable.create(new ObservableOnSubscribe<long[]>() {
                @Override
                public void subscribe(ObservableEmitter<long[]> emitter) throws Exception {
                    try {
                        if (!emitter.isDisposed()) {
                            long[] info = new long[3];
                            info[0] = jobSource.getJobCount(customer, ProjectType.Type.TYPE_OBJECT);
                            info[1] = jobSource.getJobCount(customer, ProjectType.Type.TYPE_ORDER);
                            info[2] = jobSource.getJobCount(customer, ProjectType.Type.TYPE_PROJECT);
                            emitter.onNext(info);
                            emitter.onComplete();
                        }

                    } catch (Throwable e) {
                        emitter.onError(e);
                    }
                }
            });

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<long[]>() {
                        @Override
                        public void onNext(long[] array) {
                            String text = ViewHolder.this.res.getString(R.string.customer_info, array[0], array[1], array[2]);
                            jobs.setText(text);
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
