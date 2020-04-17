package com.learning.app.md_instagram;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.learning.app.md_instagram.adapter.MyAdapter;
import com.learning.app.md_instagram.util.CircleTransformation;
import com.learning.app.md_instagram.view.RevealBackgroundView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends BaseDrawerActivity {

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @BindView(R.id.revealBackgroundView)
    RevealBackgroundView revealBackgroundView;
    @BindView(R.id.rvUserProfile)
    RecyclerView rvUserProfile;
    @BindView(R.id.vUserDetails)
    LinearLayout vUserDetails;
    @BindView(R.id.vUserStatus)
    LinearLayout vUserStatus;
    @BindView(R.id.vUserProfileRoot)
    LinearLayout vUserProfileRoot;
    @BindView(R.id.btnCreate)
    FloatingActionButton btnCreate;

    @BindView(R.id.ivUserProfile)
    ImageView ivUserProfile;
    @BindView(R.id.tlUserProfile)
    TabLayout tlUserProfile;

    private int avatarSize;
    private String profilePhoto;

//    private UserProfileAdapter userProfileAdapter;

    private List<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        initData(savedInstanceState);
        initView();
    }

    private void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 30; i++) {
            mData.add("这是第" + (i + 1) + "个条目");
        }
        avatarSize = getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
        profilePhoto = "http://b-ssl.duitang.com/uploads/item/201704/10/20170410095843_SEvMy.thumb.700_0.jpeg";
        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivUserProfile);

        setupTabs();
        setupUserProfileGrid();
//        setupRevealBackGround(savedInstanceState);
    }

    protected void initView() {
//        rvUserProfile.setLayoutManager(new LinearLayoutManager(this));
//        rvUserProfile.setAdapter(new MyAdapter(mData));
    }

    private void setupTabs() {
        tlUserProfile.addTab(tlUserProfile.newTab().setIcon(R.drawable.ic_menu_camera));
        tlUserProfile.addTab(tlUserProfile.newTab().setIcon(R.drawable.ic_menu_cc_am));
        tlUserProfile.addTab(tlUserProfile.newTab().setIcon(R.drawable.ic_menu_edit));
        tlUserProfile.addTab(tlUserProfile.newTab().setIcon(R.drawable.ic_menu_emoticons));
    }

    private void setupUserProfileGrid() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvUserProfile.setLayoutManager(staggeredGridLayoutManager);
        rvUserProfile.setAdapter(new MyAdapter(mData));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rvUserProfile.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    userProfileAdapter
                }
            });
        }
    }

    private void setupRevealBackGround(Bundle savedInstanceState) {
    }

    @OnClick(R.id.btnCreate)
    public void onViewClicked() {
    }
}
