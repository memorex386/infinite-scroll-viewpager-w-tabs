package com.memorex386.infiniteviewpagerandtabs;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by bradley.thome on 6/15/17.
 */

public class FragmentStatePagerInfiniteScroll extends InfiniteScroll<InfiniteScroll.InfiniteFragmentStatePagerAdapter, FragmentStatePagerInfiniteScroll> {


    FragmentStatePagerInfiniteScroll(Class<InfiniteFragmentStatePagerAdapter> infiniteFragmentStatePagerAdapterClass, ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infiniteFragmentStatePagerAdapterClass, viewPager, pagerAdapter, tabLayout);
    }

    FragmentStatePagerInfiniteScroll(Class<InfiniteFragmentStatePagerAdapter> infiniteFragmentStatePagerAdapterClass, ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infiniteFragmentStatePagerAdapterClass, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    FragmentStatePagerInfiniteScroll(Class<InfiniteFragmentStatePagerAdapter> infiniteFragmentStatePagerAdapterClass, ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infiniteFragmentStatePagerAdapterClass, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    FragmentStatePagerInfiniteScroll(Class<InfiniteFragmentStatePagerAdapter> infiniteFragmentStatePagerAdapterClass, ViewPager viewPager, InfinitePagerAdapter pagerAdapter, TabLayout tabLayout) {
        super(infiniteFragmentStatePagerAdapterClass, viewPager, pagerAdapter, tabLayout);
    }
}
