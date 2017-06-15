package com.memorex386.infiniteviewpagerandtabs;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by bradley.thome on 6/15/17.
 */

public class PagerInfiniteScroll extends InfiniteScroll<InfiniteScroll.InfinitePagerAdapter, PagerInfiniteScroll> {

    PagerInfiniteScroll(Class<InfinitePagerAdapter> infinitePagerAdapterClass, ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infinitePagerAdapterClass, viewPager, pagerAdapter, tabLayout);
    }

    PagerInfiniteScroll(Class<InfinitePagerAdapter> infinitePagerAdapterClass, ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infinitePagerAdapterClass, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    PagerInfiniteScroll(Class<InfinitePagerAdapter> infinitePagerAdapterClass, ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infinitePagerAdapterClass, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    PagerInfiniteScroll(Class<InfinitePagerAdapter> infinitePagerAdapterClass, ViewPager viewPager, InfinitePagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infinitePagerAdapterClass, viewPager, pagerAdapter, tabLayout);
    }
}
