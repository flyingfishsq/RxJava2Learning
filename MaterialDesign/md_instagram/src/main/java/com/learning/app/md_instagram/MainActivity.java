package com.learning.app.md_instagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseDrawerActivity {
    private static final String REVEAL_START_LOCATION = "reveal_start_location";

    @BindView(R.id.btnCreate)
    FloatingActionButton btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCreate)
    public void onViewClicked() {
        performJump(btnCreate);
    }

    private void performJump(View view) {
        //系统方法获取的location实际上是fab的左上角坐标
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        //获得水波纹扩散的中心点
        location[0] = location[0]+view.getWidth()/2;
        location[1] = location[1]+view.getHeight()/2;

        startUserProfileActivity(location,this);
        //设置转场动画，将系统的转场动画取消
        overridePendingTransition(0,0);
    }

    //location是fab的中心位置，表示水波纹扩散的中心点
    private void startUserProfileActivity(int[] startLocation, Activity act){
        Intent intent = new Intent(act,UserProfileActivity.class);
        intent.putExtra(REVEAL_START_LOCATION, startLocation);
        act.startActivity(intent);
    }
}
