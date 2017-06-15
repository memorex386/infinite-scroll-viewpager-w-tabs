package com.memorex386.infiniteviewpagerandtabs;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by bradley.thome on 6/15/17.
 */

public class CircleTabIndicator extends FrameLayout implements InfiniteScroll.CustomTab {

    private TabLayout tabLayout;

    public CircleTabIndicator(Context context) {
        super(context);
        init();
    }

    public CircleTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleTabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_circle_tab_indicator, this);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
