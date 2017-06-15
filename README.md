[ ![Download](https://api.bintray.com/packages/memorex386/maven/infinite_view_pager_w_tabs/images/download.svg) ](https://bintray.com/memorex386/maven/infinite_view_pager_w_tabs/_latestVersion)
# Infinitely Scrolling ViewPager with Optional TabLayout Integration 
An easy way to make any ViewPager, PagerAdapter (or FragmentStatePagerAdapter), and optional TabLayout into an infintely scrolling ViewPager. 

No custom Views are needed, just use what you have already built and attach these to them!!

## Gradle
    repositories
    {
    ...
    maven {
       url  'https://github.com/memorex386/infinite-scroll-viewpager-w-tabs'
    }
    ...
    }


    dependencies
    {
    ...
    compile 'com.mtsdealersolutions:infinite_view_pager_w_tabs:1.0.19'
    ...
    }



## Implementation

#### Very simple to use...


        // This is a TabLayout that we include that uses circles for Page Indicators, or use your own!
        circleIndicator = (CircleTabIndicator)findViewById(R.id.circles);

        // Just a standard ViewPager!
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Just a standard PagerAdapter (or use a FragmentStatePagerAdapter)!
        testPager = new TestPager(this);

        // Infinite Scroll instance is created and wraps ViewPager, PagerAdapter...
        final PagerInfiniteScroll pagerInfiniteScroll = InfiniteScroll.InfiniteScroll(viewPager, testPager)
        // ... and attaching the optional TabLayout ...
        .setTabLayout(circleIndicator)
        // ... The infinite count is set to 200 by default, larger numbers eat more memory and processing ...
        .setInfiniteCount(1234)
        // ... and attach the PagerAdapter to the ViewPager!
        .attachAdapter();

#### You can always get the InfinitePagerAdapter (which also holds your real adapter)!

		pagerInfiniteScroll.getInfinitePagerAdapter().getRealPosition(456);
		pagerInfiniteScroll.getInfinitePagerAdapter().getRealAdapter();

#### Now for fun we will disable infinite scroll after 10 seconds, then re-enable ...

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // ... Disabling infinite scroll here ...
                pagerInfiniteScroll.setDisableInfiniteScroll(true);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // ... and re-enabling it again!
                        pagerInfiniteScroll.setDisableInfiniteScroll(false);
                    }
                }, 10000);

            }
        }, 10000);
        
## Dont use These!
#### The following code is no longer needed as it is handled by the InfiniteScroll instance...
		
        viewPager.setAdapter(testPager);
        tabLayout.setTabsFromPagerAdapter(testPager);
        tabLayout.setupWithViewPager(viewPager);
		
