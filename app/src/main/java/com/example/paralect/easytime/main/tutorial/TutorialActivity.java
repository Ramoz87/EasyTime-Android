package com.example.paralect.easytime.main.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.login.LoginActivity;
import com.example.paralect.easytime.model.Constants;
import com.example.paralect.easytime.model.TutorialItem;
import com.example.paralect.easytime.utils.TinyDB;
import com.rd.PageIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 09/02/2018.
 */

public class TutorialActivity extends AppCompatActivity {

    @BindView(R.id.tutorial_view_pager) ViewPager viewPager;
    @BindView(R.id.tutorial_page_indicator) PageIndicatorView pageIndicatorView;
    @BindView(R.id.tutorial_main_text_view) TextView mainTextView;
    @BindView(R.id.tutorial_details_text_view) TextView detailsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        showTutorial();
    }

    private void showTutorial() {
        List<TutorialItem> items = TutorialItem.getMocks(this);
        final TutorialPagerAdapter adapter = new TutorialPagerAdapter(items);
        ViewPager.OnPageChangeListener listener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                TutorialItem item = adapter.getTutorialItem(position);
                mainTextView.setText(item.getTitle());
                detailsTextView.setText(item.getDetails());

            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(listener);

        pageIndicatorView.setViewPager(viewPager);
        pageIndicatorView.setDynamicCount(true);
        listener.onPageSelected(0);
    }

    @OnClick(R.id.tutorial_skip_button)
    public void onClickSkip() {

        TinyDB tinyDB = new TinyDB(TutorialActivity.this);
        boolean isLaunched = tinyDB.getBoolean(Constants.TUTORIAL_LAUNCH, false);

        if (!isLaunched) {
            tinyDB.putBoolean(Constants.TUTORIAL_LAUNCH, true);
            Intent intent = new Intent(TutorialActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
