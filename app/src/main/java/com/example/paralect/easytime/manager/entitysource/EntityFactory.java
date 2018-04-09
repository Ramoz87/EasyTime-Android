package com.example.paralect.easytime.manager.entitysource;

import com.example.paralect.easytime.model.Object;
import com.example.paralect.easytime.model.User;
import com.example.paralect.easytime.utils.Logger;
import com.paralect.datacsv.CSVHelper;
import com.paralect.datacsv.request.CSVRequest;
import com.paralect.datacsv.request.CustomerRequestCSV;
import com.paralect.datacsv.request.MaterialRequestCSV;
import com.paralect.datacsv.request.ObjectRequestCSV;
import com.paralect.datacsv.request.OrderRequestCSV;
import com.paralect.datacsv.request.ProjectsRequestCSV;
import com.paralect.datacsv.request.TypeRequestCSV;
import com.paralect.datacsv.request.UserRequestCSV;
import com.paralect.datasource.ormlite.ORMLiteRequest;
import com.paralect.datasource.retrofit.DownloadFileRequest;
import com.paralect.easytimedataormlite.request.CustomerRequestORM;
import com.paralect.easytimedataormlite.request.MaterialRequestORM;
import com.paralect.easytimedataormlite.request.ObjectRequestORM;
import com.paralect.easytimedataormlite.request.OrderRequestORM;
import com.paralect.easytimedataormlite.request.ProjectRequestORM;
import com.paralect.easytimedataormlite.request.TypeRequestORM;
import com.paralect.easytimedataormlite.request.UserRequestORM;
import com.paralect.easytimedataretrofit.NetworkHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.EasyTimeApplication.getContext;
import static com.paralect.datasource.rx.DataSourceRx.NOTHING;

/**
 * Created by Oleg Tarashkevich on 06/04/2018.
 */

public class EntityFactory extends CSVSource {

    private final String USERS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/users.csv";
    private final String TYPES_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/types.csv";
    private final String CUSTOMERS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/customers.csv";
    private final String MATERIALS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/materials.csv";
    private final String ORDERS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/orders.csv";
    private final String OBJECTS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/objects.csv";
    private final String PROJECTS_URL = "https://api.backendless.com/3AF81CDE-075B-72EF-FF10-2BD8170BA800/3884AF06-8F57-7BD9-FF13-3FDE4BBE8F00/files/projects.csv";

    private final NetworkHelper networkHelper = new NetworkHelper();
    private final CSVHelper csvFileHelper = new CSVHelper();
    private final ExecutorService DOWNLOAD_EXECUTOR = Executors.newFixedThreadPool(2);

    public Flowable<String> download() {
        List<Single<String>> sources = Arrays.asList(

                extractData(USERS_URL, getDownloadRequest(), new UserRequestCSV(), new UserRequestORM()),
                extractData(TYPES_URL, getDownloadRequest(), new TypeRequestCSV(), new TypeRequestORM()),
                extractData(CUSTOMERS_URL, getDownloadRequest(), new CustomerRequestCSV(), new CustomerRequestORM()),
                extractData(MATERIALS_URL, getDownloadRequest(), new MaterialRequestCSV(), new MaterialRequestORM()),
                extractData(ORDERS_URL, getDownloadRequest(), new OrderRequestCSV(), new OrderRequestORM()),
                extractData(OBJECTS_URL, getDownloadRequest(), new ObjectRequestCSV(), new ObjectRequestORM()),
                extractData(PROJECTS_URL, getDownloadRequest(), new ProjectsRequestCSV(), new ProjectRequestORM()

                ));

        return Single.concat(sources);
    }

    private <E> Single<String> extractData(final String url, final DownloadFileRequest networkQuery, final CSVRequest csvQuery, final ORMLiteRequest ormQuery) {

        networkQuery.setQuery(url);

        // Download file
        return networkHelper.getDataAsync(networkQuery)
                // convert from csv to list
                .map(new Function<File, List<E>>() {
                    @Override
                    public List<E> apply(File file) throws Exception {
                        csvQuery.setParameter(file);
                        return csvFileHelper.getList(csvQuery);
                    }
                })
                // save list to database
                .map(new Function<List<E>, String>() {
                    @Override
                    public String apply(List<E> users) throws Exception {
                        fillData(users, ormQuery);
                        return url;
                    }
                })
                .subscribeOn(Schedulers.from(DOWNLOAD_EXECUTOR));
    }

    private DownloadFileRequest getDownloadRequest() {
        return new DownloadFileRequest(getContext());
    }
    
}
