package com.learning.app.md_instagram;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.learning.app.md_instagram.adapter.UserProfileAdapter;
import com.learning.app.md_instagram.util.CircleTransformation;
import com.learning.app.md_instagram.view.RevealBackgroundView;
import com.squareup.picasso.Picasso;

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

    private UserProfileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        initData();
        initView(savedInstanceState);
    }

    private void initData() {
        avatarSize = getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
        profilePhoto = "http://b-ssl.duitang.com/uploads/item/201704/10/20170410095843_SEvMy.thumb.700_0.jpeg";
        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivUserProfile);
    }

    protected void initView(Bundle savedInstanceState) {
        setupTabs();
        setupUserProfileGrid();
        setupRevealBackGround(savedInstanceState);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rvUserProfile.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    adapter.setLockedAnimations(true);//锁定动画
                }
            });
        }
    }

    private void setupRevealBackGround(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            //注意动画的启动时间
            revealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //不删除就会一直监听
                    revealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                    Intent intent = getIntent();
                    if (intent.hasExtra(MainActivity.REVEAL_START_LOCATION)) {
                        int[] location = getIntent().getIntArrayExtra(MainActivity.REVEAL_START_LOCATION);
                        revealBackgroundView.startFromLocation(location);
                    }
                    return true;
                }
            });
        } else {
            revealBackgroundView.setToFinishedFrame();
            adapter.setLockedAnimations(true);
        }
        revealBackgroundView.setOnStateChangeListener(new RevealBackgroundView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if (state == RevealBackgroundView.STATE_FINISHED) {
                    tlUserProfile.setVisibility(View.VISIBLE);
                    rvUserProfile.setVisibility(View.VISIBLE);
                    vUserProfileRoot.setVisibility(View.VISIBLE);
                    //显示数据
                    if (adapter == null) {
                        adapter = new UserProfileAdapter(UserProfileActivity.this);
                        rvUserProfile.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    //开启其它view的动画
                    animateUserProfile();
                } else {
                    tlUserProfile.setVisibility(View.INVISIBLE);
                    rvUserProfile.setVisibility(View.INVISIBLE);
                    vUserProfileRoot.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void animateUserProfile() {
        //向下平移的高度
        vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
        ivUserProfile.setTranslationY(-ivUserProfile.getHeight());
        tlUserProfile.setTranslationY(-tlUserProfile.getHeight());
        vUserDetails.setTranslationY(-vUserDetails.getHeight());
        vUserStatus.setAlpha(0);

        //从设置的位置平移到最终显示的位置
        vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
        ivUserProfile.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
        vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
        vUserStatus.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
        tlUserProfile.animate().translationY(0).setDuration(300)
                .setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    @OnClick(R.id.btnCreate)
    public void onViewClicked() {
//        int[] loc = new int[2];
//        btnCreate.getLocationOnScreen(loc);
//        loc[0] = loc[0] + btnCreate.getWidth();
//        loc[1] = loc[1] + btnCreate.getHeight();
//        revealBackgroundView.startFromLocation(loc);
    }
}
