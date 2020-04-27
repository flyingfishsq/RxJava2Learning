package com.learning.app.selfdefinebehavior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_a, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_a:
                ViewCompat.offsetTopAndBottom(tvA, 3);
                break;
            case R.id.fab:
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                break;
        }
    }

}
