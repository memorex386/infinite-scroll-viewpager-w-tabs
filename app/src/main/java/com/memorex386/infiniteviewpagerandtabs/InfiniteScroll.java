package com.memorex386.infiniteviewpagerandtabs;

import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class InfiniteScroll {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private InfinitePagerInterface infinitePagerAdapter;

    public InfiniteScroll(ViewPager viewPager, PagerAdapter pagerAdapter) {
        this(viewPager, pagerAdapter, null);
    }


    public InfiniteScroll(ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfinitePagerAdapter(pagerAdapter);
    }

    public InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter) {
        this(viewPager, fragmentManager, pagerAdapter, null);
    }

    public InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfiniteFragmentStatePagerAdapter(fragmentManager, pagerAdapter);
    }

    public InfiniteScroll setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
        if (tabLayout == null) return this;

        for (int i = 0; i < infinitePagerAdapter.getRealCount(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = InfiniteScroll.this.tabLayout.getSelectedTabPosition();
                int currentPosition = viewPager.getCurrentItem();
                int fakeCurrentPosition = infinitePagerAdapter.getRealPosition(currentPosition);
                int addAmount = tabPosition - fakeCurrentPosition;
                viewPager.setCurrentItem(currentPosition + addAmount, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = InfiniteScroll.this.tabLayout.getTabAt(infinitePagerAdapter.getRealPosition(position));
                if (tab != null) tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return this;
    }

    /*
    This will attach the adapter to the viewPager and TabLayout (if applicable)
     */
    public InfiniteScroll attachAdapter() {
        viewPager.setAdapter((PagerAdapter) infinitePagerAdapter);

        setTabLayout(tabLayout);

        int half = infinitePagerAdapter.getCount() / 2;
        half = half - infinitePagerAdapter.getRealPosition(half);
        viewPager.setCurrentItem(half);

        return this;
    }

    public InfiniteScroll setTabIndicator(TabIndicator tabIndicator){
        tabLayout.setBackground();
    }


    public static class InfinitePagerAdapter extends PagerAdapter implements InfinitePagerInterface<PagerAdapter> {

        private PagerAdapter pagerAdapter;

        public InfinitePagerAdapter(PagerAdapter pagerAdapter) {
            this.pagerAdapter = pagerAdapter;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        public int getRealCount() {
            return pagerAdapter.getCount();
        }

        @Override
        public int getRealPosition(int infinitePosition) {
            return infinitePosition % getRealCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return pagerAdapter.instantiateItem(container, getRealPosition(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            pagerAdapter.destroyItem(container, getRealPosition(position), object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return pagerAdapter.isViewFromObject(view, object);
        }

        @Override
        public PagerAdapter getRealAdapter() {
            return pagerAdapter;
        }
    }

    public static class InfiniteFragmentStatePagerAdapter extends FragmentStatePagerAdapter implements InfinitePagerInterface<FragmentStatePagerAdapter> {

        FragmentStatePagerAdapter fragmentStatePagerAdapter;

        public InfiniteFragmentStatePagerAdapter(FragmentManager fm, FragmentStatePagerAdapter fragmentStatePagerAdapter) {
            super(fm);
            this.fragmentStatePagerAdapter = fragmentStatePagerAdapter;
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getRealCount() {
            return fragmentStatePagerAdapter.getCount();
        }

        @Override
        public int getRealPosition(int infinitePosition) {
            return infinitePosition % getRealCount();
        }

        @Override
        public FragmentStatePagerAdapter getRealAdapter() {
            return fragmentStatePagerAdapter;
        }
    }

    private interface InfinitePagerInterface<T extends PagerAdapter> {
        int getRealCount();

        int getCount();

        int getRealPosition(int infinitePosition);

        T getRealAdapter();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public PagerAdapter getRealPagerAdapter() {
        return infinitePagerAdapter.getRealAdapter();
    }

    public InfinitePagerInterface getInfinitePagerAdapter() {
        return infinitePagerAdapter;
    }

    public enum TabIndicator {
        CIRCLE(R.drawable.tab_dot_selector);

        @DrawableRes
        private int drawableRes;

        TabIndicator(int drawableRes) {
            this.drawableRes = drawableRes;
        }

        @DrawableRes
        public int getDrawableRes() {
            return drawableRes;
        }
    }
}
