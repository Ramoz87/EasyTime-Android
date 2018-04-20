package com.example.paralect.easytime.manager.entitysource;

import com.paralect.datacsv.CSVHelper;
import com.paralect.datacsv.request.CSVRequest;
import com.paralect.datacsv.request.CustomerRequestCSV;
import com.paralect.datacsv.request.MaterialRequestCSV;
import com.paralect.datacsv.request.ObjectRequestCSV;
import com.paralect.datacsv.request.OrderRequestCSV;
import com.paralect.datacsv.request.ProjectsRequestCSV;
import com.paralect.datacsv.request.TypeRequestCSV;
import com.paralect.datacsv.request.UserRequestCSV;
import com.paralect.datasource.database.DatabaseRequest;
import com.paralect.datasource.retrofit.DownloadFileRequest;
import com.paralect.database.request.CustomerRequestORM;
import com.paralect.database.request.MaterialRequestORM;
import com.paralect.database.request.ObjectRequestORM;
import com.paralect.database.request.OrderRequestORM;
import com.paralect.database.request.ProjectRequestORM;
import com.paralect.database.request.TypeRequestORM;
import com.paralect.database.request.UserRequestORM;
import com.paralect.easytimedataretrofit.NetworkHelper;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.paralect.easytime.EasyTimeApplication.getContext;

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

    private <E> Single<String> extractData(final String url, final DownloadFileRequest networkQuery, final CSVRequest csvQuery, final DatabaseRequest ormQuery) {

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