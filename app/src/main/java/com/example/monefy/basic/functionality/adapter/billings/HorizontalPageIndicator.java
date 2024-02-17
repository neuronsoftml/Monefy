package com.example.monefy.basic.functionality.adapter.billings;



import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monefy.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalPageIndicator extends LinearLayout {

    private Context context;
    private int pageCount;
    private int currentPage;

    private List<ImageView> indicators;

    public HorizontalPageIndicator(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public HorizontalPageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HorizontalPageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        indicators = new ArrayList<>();
    }

    public void setPageCount(int count) {
        pageCount = count;
        createIndicators();
    }

    public void setCurrentPage(int position) {
        currentPage = position;
        updateIndicators();
    }

    private void createIndicators() {
        indicators.clear();
        removeAllViews();

        for (int i = 0; i < pageCount; i++) {
            ImageView indicator = new ImageView(context);
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(params);
            indicator.setImageResource(i == currentPage ? R.drawable.icon_selected_indicator : R.drawable.icon_unselected_indicator);
            addView(indicator);
            indicators.add(indicator);
        }
    }

    private void updateIndicators() {
        for (int i = 0; i < pageCount; i++) {
            indicators.get(i).setImageResource(i == currentPage ? R.drawable.icon_selected_indicator : R.drawable.icon_unselected_indicator);
        }
    }
}
