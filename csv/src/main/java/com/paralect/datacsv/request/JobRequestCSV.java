package com.paralect.datacsv.request;

import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CalendarUtils;

import java.util.Date;

/**
 * Created by Oleg Tarashkevich on 05/04/2018.
 */

abstract class JobRequestCSV<AP> extends CSVRequest<AP> {

    void fillJob(Job job, String[] fields) {
//        Log.d("Help", fields.toString());
        job.setEntityType(fields[0]);
        job.setId(fields[1]);
        job.setCustomerId(fields[2]);
        job.setStatusId(fields[3]);
        job.setTypeId(fields[4]);
        job.setNumber(Integer.valueOf(fields[5]));
        job.setName(fields[6]);
        job.setInformation(fields[7]);

        String memberIds = fields[8];
        memberIds = memberIds.replace("\"", "");
        String[] ids = memberIds.split(",[ ]*");
        if (ids.length == 1 && ids[0].isEmpty()) ids = new String[0];
        job.setMemberIds(ids);
        // fields[8]?
        job.setCurrency(fields[10]);

        // random date
        Date date = CalendarUtils.nextDate();
//                Date date = new Date();
//                String dateString = CalendarUtils.stringFromDate(date, CalendarUtils.SHORT_DATE_FORMAT);
//                Log.d(TAG, "new date for job: " + dateString);
        job.setDate(date.getTime());
    }
}
