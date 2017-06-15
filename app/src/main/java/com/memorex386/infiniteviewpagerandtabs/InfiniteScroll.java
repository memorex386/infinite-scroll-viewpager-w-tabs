package com.memorex386.infiniteviewpagerandtabs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class InfiniteScroll<T extends PagerAdapter, ISitem extends InfiniteScroll> {

    private static final int DEFAULT_INFINITE_COUNT = 200;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private InfinitePagerInterface infinitePagerAdapter;
    private Class<T> tClass;

    private static int infiniteCount = DEFAULT_INFINITE_COUNT;
    private static boolean disableInfiniteScroll = false;

    private boolean attachAdapterRan = false;

    public static PagerInfiniteScroll InfiniteScroll(ViewPager viewPager, PagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, pagerAdapter, null);
    }

    public static PagerInfiniteScroll InfiniteScroll(ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new PagerInfiniteScroll(InfinitePagerAdapter.class, viewPager, pagerAdapter, tabLayout);
    }

    public static FragmentStatePagerInfiniteScroll InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, fragmentManager, pagerAdapter, null);
    }

    public static FragmentStatePagerInfiniteScroll InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new FragmentStatePagerInfiniteScroll(InfiniteFragmentStatePagerAdapter.class, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    public static FragmentStatePagerInfiniteScroll InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, fragmentManager, pagerAdapter, null);
    }

    public static FragmentStatePagerInfiniteScroll InfiniteScroll(ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new FragmentStatePagerInfiniteScroll(InfiniteFragmentStatePagerAdapter.class, viewPager, fragmentManager, pagerAdapter, tabLayout);
    }

    public static PagerInfiniteScroll InfiniteScroll(ViewPager viewPager, InfinitePagerAdapter pagerAdapter) {
        return InfiniteScroll(viewPager, pagerAdapter, null);
    }

    public static PagerInfiniteScroll InfiniteScroll(ViewPager viewPager, InfinitePagerAdapter pagerAdapter, TabLayout tabLayout) {
        return new PagerInfiniteScroll(InfinitePagerAdapter.class, viewPager, pagerAdapter, tabLayout);
    }

    InfiniteScroll(Class<T> tClass, ViewPager viewPager, PagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfinitePagerAdapter(pagerAdapter);
        this.tClass = tClass;
    }

    InfiniteScroll(Class<T> tClass, ViewPager viewPager, FragmentManager fragmentManager, FragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfiniteFragmentStatePagerAdapter(fragmentManager, pagerAdapter);
        this.tClass = tClass;
    }

    InfiniteScroll(Class<T> tClass, ViewPager viewPager, FragmentManager fragmentManager, InfiniteFragmentStatePagerAdapter pagerAdapter, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        infinitePagerAdapter = new InfiniteFragmentStatePagerAdapter(fragmentManager, pagerAdapter);
        this.tClass = tClass;
    }

    InfiniteScroll(Class<T> tClass, ViewPager viewPager, InfinitePagerAdapter pagerAdapter, TabLayout tabLayout) {
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
    public ISitem setTabLayout(TabLayout tabLayout) {
        return setTabLayout(tabLayout, false);
    }

    /**
     * This method will take a TabLayout and attach it to the ViewPager
     *
     * @param tabLayout TabLayout to attach, all pre-existing tabs will be removed
     */
    public ISitem setTabLayout(CustomTab tabLayout) {
        return setTabLayout(tabLayout.getTabLayout(), false);
    }


    /**
     * This method will take a TabLayout and attach it to the ViewPager
     *
     * @param tabLayout         TabLayout to attach
     * @param dontRemoveAllTabs The TabLayout is cleared of existing tabs by default,
     *                          set to false and it will not clear the tabs before it
     *                          creates the new ones
     */
    public ISitem setTabLayout(CustomTab tabLayout, boolean dontRemoveAllTabs) {
        return setTabLayout(tabLayout.getTabLayout(), false);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int tabPosition = InfiniteScroll.this.tabLayout.getSelectedTabPosition();
            int currentPosition = viewPager.getCurrentItem();
            int fakeCurrentPosition = infinitePagerAdapter.getRealPosition(currentPosition);
            int addAmount = tabPosition - fakeCurrentPosition;
            if (viewPager.getCurrentItem() != currentPosition + addAmount)
                viewPager.setCurrentItem(currentPosition + addAmount, true);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
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
    };

    /**
     * This method will take a TabLayout and attach it to the ViewPager
     *
     * @param tabLayout         TabLayout to attach
     * @param dontRemoveAllTabs The TabLayout is cleared of existing tabs by default,
     *                          set to false and it will not clear the tabs before it
     *                          creates the new ones
     */
    public ISitem setTabLayout(TabLayout tabLayout, boolean dontRemoveAllTabs) {
        this.tabLayout = tabLayout;
        if (tabLayout == null) return (ISitem) this;

        if (!dontRemoveAllTabs) tabLayout.removeAllTabs();

        for (int i = 0; i < infinitePagerAdapter.getRealCount(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tabLayout.addTab(tab);
        }

        tabLayout.removeOnTabSelectedListener(tabSelectedListener);
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        viewPager.removeOnPageChangeListener(onPageChangeListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        return (ISitem) this;
    }

    /*
    * This will attach the adapter to the viewPager and TabLayout (if a TabLayout has been attached)
     */
    public ISitem attachAdapter() {

        attachAdapterRan = true;

        viewPager.setAdapter((PagerAdapter) infinitePagerAdapter);

        setTabLayout(tabLayout);

        setHalfPoint(0);

        return (ISitem) this;
    }

    private void setHalfPoint(int startingPoint) {
        int half = infinitePagerAdapter.getCount() / 2;
        half = half - infinitePagerAdapter.getRealPosition(half);
        viewPager.setCurrentItem(half + startingPoint, false);
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param drawableRes the Drawable to use for the TabLayout,
     *                    best if this is a selector drawable using state-pressed
     */
    @SuppressWarnings("deprecation")
    public ISitem setTabIndicator(@DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tabLayout.setBackground(ContextCompat.getDrawable(tabLayout.getContext(), drawableRes));
        } else {
            tabLayout.setBackgroundDrawable(ContextCompat.getDrawable(tabLayout.getContext(), drawableRes));
        }
        return (ISitem) this;
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param selectedDrawableRes   The drawable to display when selected
     * @param unselectedDrawableRes The drawable to display when unselected
     */
    @SuppressWarnings("deprecation")
    public ISitem setTabIndicator(@DrawableRes int selectedDrawableRes, @DrawableRes int unselectedDrawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tabLayout.setBackground(createStateListDrawable(tabLayout.getContext(), selectedDrawableRes, unselectedDrawableRes));
        } else {
            tabLayout.setBackgroundDrawable(createStateListDrawable(tabLayout.getContext(), selectedDrawableRes, unselectedDrawableRes));
        }
        return (ISitem) this;
    }

    /**
     * This method will set the Tab Indicators using a predefined method
     *
     * @param selectedDrawableRes   The drawable to display when selected
     * @param unselectedDrawableRes The drawable to display when unselected
     */
    public static StateListDrawable createStateListDrawable(Context context, @DrawableRes int selectedDrawableRes, @DrawableRes int unselectedDrawableRes) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(context, selectedDrawableRes));
        res.addState(new int[]{}, ContextCompat.getDrawable(context, unselectedDrawableRes));
        return res;
    }

    /**
     * This method will return the infinite amount
     * (which is a finite number, by default it is 200.
     * This is because a larger number will increase processing power and memory usage)
     */
    public int getInfiniteCount() {
        return infiniteCount;
    }

    /**
     * This method will set the 'infinte' amount
     * (which is a finite number, by default it is 200.
     * This is because a larger number will increase processing power and memory usage.
     * This means you will have to scroll roughly 100 times to the start or the end to reach the finite ending of 200)
     */
    public ISitem setInfiniteCount(int infiniteCount) {
        if (attachAdapterRan)
            throw new RuntimeException("This should be set before attachAdapter() is ran");
        if (infiniteCount == InfiniteScroll.infiniteCount) return (ISitem) this;
        InfiniteScroll.infiniteCount = infiniteCount;
        return (ISitem) this;
    }

    public boolean isDisableInfiniteScroll() {
        return disableInfiniteScroll;
    }

    public ISitem setDisableInfiniteScroll(boolean disableInfiniteScroll) {
        if (disableInfiniteScroll == InfiniteScroll.disableInfiniteScroll) return (ISitem) this;
        int getPosition = infinitePagerAdapter.getRealPosition(viewPager.getCurrentItem());

        if (disableInfiniteScroll) {
            viewPager.setCurrentItem(getPosition, false);
            InfiniteScroll.disableInfiniteScroll = true;
            ((PagerAdapter) infinitePagerAdapter).notifyDataSetChanged();
        } else {
            InfiniteScroll.disableInfiniteScroll = false;
            ((PagerAdapter) infinitePagerAdapter).notifyDataSetChanged();
            setHalfPoint(getPosition);
        }

        return (ISitem) this;
    }

    public static class InfinitePagerAdapter extends PagerAdapter implements InfinitePagerInterface<PagerAdapter> {

        private PagerAdapter pagerAdapter;

        public InfinitePagerAdapter(PagerAdapter pagerAdapter) {
            this.pagerAdapter = pagerAdapter;
        }

        /**
         * This method will return the infinite count
         */
        @Override
        public int getCount() {
            return disableInfiniteScroll ? getRealCount() : infiniteCount;
        }

        /**
         * This method will return the count of the adapter
         */
        public int getRealCount() {
            return pagerAdapter.getCount();
        }

        /**
         * This method will return the real position of the real adapter
         */
        @Override
        public int getRealPosition(int infinitePosition) {
            return infinitePosition % getRealCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return pagerAdapter.instantiateItem(container, getRealPosition(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            return pagerAdapter.instantiateItem(container, getRealPosition(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            pagerAdapter.destroyItem(container, getRealPosition(position), object);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            pagerAdapter.destroyItem(container, getRealPosition(position), object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return pagerAdapter.isViewFromObject(view, object);
        }

        /**
         * This method will return the real PagerAdapter
         */
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

        /**
         * This method will return the infinite count
         */
        @Override
        public int getCount() {
            return disableInfiniteScroll ? getRealCount() : infiniteCount;
        }

        /**
         * This method will return the count of the adapter
         */
        @Override
        public int getRealCount() {
            return fragmentStatePagerAdapter.getCount();
        }

        /**
         * This method will return the real position of the real adapter
         */
        @Override
        public int getRealPosition(int infinitePosition) {
            return infinitePosition % getRealCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return fragmentStatePagerAdapter.instantiateItem(container, getRealPosition(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            return fragmentStatePagerAdapter.instantiateItem(container, getRealPosition(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            fragmentStatePagerAdapter.destroyItem(container, getRealPosition(position), object);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            fragmentStatePagerAdapter.destroyItem(container, getRealPosition(position), object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return fragmentStatePagerAdapter.isViewFromObject(view, object);
        }

        /**
         * This method will return the real FragmentStatePagerAdapter
         */
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
         */
        int getRealPosition(int infinitePosition);

        /**
         * This method will return the real adapter
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
        return (T) infinitePagerAdapter;
    }

    public static int getDp(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public interface CustomTab {
        TabLayout getTabLayout();
    }
}
