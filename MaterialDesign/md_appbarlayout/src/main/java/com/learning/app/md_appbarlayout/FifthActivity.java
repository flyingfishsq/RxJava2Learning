package com.learning.app.md_appbarlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.learning.app.md_appbarlayout.fragment.NewsDetailFragment;

public class FifthActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private String[] title = {
            "头条",
            "新闻",
            "娱乐",
            "体育",
            "科技",
            "美女",
            "财经",
            "汽车",
            "房子",
            "头条"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        //1.TabLayout和Viewpager关联
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //2.ViewPager滑动关联tabLayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //设置tabLayout的标签来自于PagerAdapter
        tabLayout.setTabsFromPagerAdapter(adapter);

        viewPager.setAdapter(adapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new NewsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title[position]);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return title.length;
        }

    }

}
