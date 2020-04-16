package com.learning.app.md_instagram;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.learning.app.md_instagram.adapter.MyAdapter;
import com.learning.app.md_instagram.view.RevealBackgroundView;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends BaseDrawerActivity {

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    RevealBackgroundView revealBackgroundView;
    RecyclerView rvUserProfile;

    TabLayout tlUserProfile;

    ImageViewCompat ivUserProfile;

    View vUserDetails;
    View vUserStatus;
    View vUserProfileRoot;

    private int avatarSize;
    private String profilePhoto;
    private MyAdapter userProfileAdapter;
//    private UserProfileAdapter userProfileAdapter;


    private List<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            mData.add("这是第" + (i + 1) + "个条目");
        }
    }
    protected void initView() {
        rvUserProfile = findViewById(R.id.rvUserProfile);
        rvUserProfile.setLayoutManager(new LinearLayoutManager(this));
        rvUserProfile.setAdapter(new MyAdapter(mData));
    }
}
