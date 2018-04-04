package com.example.paralect.easytime.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.paralect.easytimemodel.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 09/02/2018.
 */

public class TutorialItem {

    private int imageResId;
    private String title;
    private String details;

    public TutorialItem(int imageResId, String title, String details) {
        this.imageResId = imageResId;
        this.title = title;
        this.details = details;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public static List<TutorialItem> getMocks(Context context) {
        List<TutorialItem> items = new ArrayList<>();

        Resources resources = context.getResources();
        TypedArray images = resources.obtainTypedArray(R.array.tutorial_images);
        List<String> titles = Arrays.asList(resources.getStringArray(R.array.tutorial_titles));
        List<String> details = Arrays.asList(resources.getStringArray(R.array.tutorial_details));

        for (int i = 0; i < titles.size(); i++) {
            int drawableRes = images.getResourceId(i, -1);
            String title = titles.get(i);
            String detail = details.get(i);
            TutorialItem item = new TutorialItem(drawableRes, title, detail);
            items.add(item);
        }

        // recycle the array
        images.recycle();
        return items;
    }
}
