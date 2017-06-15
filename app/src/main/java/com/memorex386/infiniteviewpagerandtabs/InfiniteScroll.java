package com.memorex386.infiniteviewpagerandtabs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class InfiniteScroll<T extends PagerAdapter> {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private InfinitePagerInterface infinitePagerAdapter;
    private Class<T> tClass;

    public static InfiniteScroll<InfinitePagerAdapter> InfiniteScroll(ViewPager viewPager, PagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, pagerAdapter, null);
    }

    public static InfiniteScroll<InfinitePagerAdapter> InfiniteScroll(ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new InfiniteScroll<>(InfinitePagerAdapter.class, viewPager, pagerAdapter, tabLayout);
    }

    public static InfiniteScroll<InfiniteFragmentStatePagerAdapter> InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, fragmentManager, pagerAdapter, null);
    }

    public static InfiniteScroll<InfiniteFragmentStatePagerAdapter> InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new InfiniteScroll<>(InfiniteFragmentStatePagerAdapter.class, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    public static InfiniteScroll<InfiniteFragmentStatePagerAdapter> InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, fragmentManager, pagerAdapter, null);
    }

    public static InfiniteScroll<InfiniteFragmentStatePagerAdapter> InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new InfiniteScroll<>(InfiniteFragmentStatePagerAdapter.class, viewPager,fragmentManager, pagerAdapter, tabLayout);
    }

    public static InfiniteScroll<InfinitePagerAdapter> InfiniteScroll(ViewPager viewPager, InfinitePagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, pagerAdapter, null);
    }

    public static InfiniteScroll<InfinitePagerAdapter> InfiniteScroll(ViewPager viewPager, InfinitePagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new InfiniteScroll<>(InfinitePagerAdapter.class, viewPager, pagerAdapter, tabLayout);
    }

    private InfiniteScroll(Class<T> tClass, ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfinitePagerAdapter(pagerAdapter);
        this.tClass = tClass;
    }

    private InfiniteScroll(Class<T> tClass, ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfiniteFragmentStatePagerAdapter(fragmentManager, pagerAdapter);
        this.tClass = tClass;
    }

    private InfiniteScroll(Class<T> tClass, ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfiniteFragmentStatePagerAdapter(fragmentManager, pagerAdapter);
        this.tClass = tClass;
    }

    private InfiniteScroll(Class<T> tClass, ViewPager viewPager, InfinitePagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfinitePagerAdapter(pagerAdapter);
        this.tClass = tClass;
    }

    /**
     * This method will take a TabLayout and attach it to the ViewPager
     *
     * @param tabLayout TabLayout to attach, all pre-existing tabs will be removed
     */
    public InfiniteScroll setTabLayout(TabLayout tabLayout){
        return setTabLayout(tabLayout, false);
    }


    /**
     * This method will take a TabLayout and attach it to the ViewPager
     *
     * @param tabLayout TabLayout to attach
     * @param dontRemoveAllTabs The TabLayout is cleared of existing tabs by default,
     *                          set to false and it will not clear the tabs before it
     *                          creates the new ones
     */
    public InfiniteScroll setTabLayout(TabLayout tabLayout, boolean dontRemoveAllTabs) {
        this.tabLayout = tabLayout;
        if (tabLayout == null) return this;

       if (!dontRemoveAllTabs) tabLayout.removeAllTabs();

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
    * This will attach the adapter to the viewPager and TabLayout (if a TabLayout has been attached)
     */
    public InfiniteScroll attachAdapter() {
        viewPager.setAdapter((PagerAdapter) infinitePagerAdapter);

        setTabLayout(tabLayout);

        int half = infinitePagerAdapter.getCount() / 2;
        half = half - infinitePagerAdapter.getRealPosition(half);
        viewPager.setCurrentItem(half);

        return this;
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param tabIndicator predefined tab indicator
     */
    public InfiniteScroll setTabIndicator(@NonNull TabIndicator tabIndicator){
       return setTabIndicator(tabIndicator.drawableRes);
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param drawableRes the Drawable to use for the TabLayout,
     *                    best if this is a selector drawable using state-pressed
     */
    public InfiniteScroll setTabIndicator(@DrawableRes int drawableRes){
        tabLayout.setBackground(ContextCompat.getDrawable(tabLayout.getContext(), drawableRes));
        return this;
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param selectedDrawableRes The drawable to display when selected
     * @param unselectedDrawableRes The drawable to display when unselected
     */
    public InfiniteScroll setTabIndicator(@DrawableRes int selectedDrawableRes, @DrawableRes int unselectedDrawableRes) {
        tabLayout.setBackground(createStateListDrawable(tabLayout.getContext(), selectedDrawableRes, unselectedDrawableRes));
        return this;
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param selectedDrawableRes The drawable to display when selected
     * @param unselectedDrawableRes The drawable to display when unselected
     */
    public static StateListDrawable createStateListDrawable(Context context, @DrawableRes int selectedDrawableRes, @DrawableRes int unselectedDrawableRes) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(context, selectedDrawableRes));
        res.addState(new int[]{}, ContextCompat.getDrawable(context, unselectedDrawableRes));
        return res;
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

    public interface InfinitePagerInterface<T extends PagerAdapter> {
        /**
         * This method will return the count of the adapter
         */
        int getRealCount();

        /**
         * This method will return the infinite count
         */
        int getCount();

        /**
         * This method will return the real position of the real adapter
         *
         */
        int getRealPosition(int infinitePosition);

        /**
         * This method will return the real adapter
         *
         */
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

    public T getInfinitePagerAdapter() {
        return (T)infinitePagerAdapter;
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
