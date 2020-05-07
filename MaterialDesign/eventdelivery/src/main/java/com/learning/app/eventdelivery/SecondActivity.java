package com.learning.app.eventdelivery;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.learning.app.eventdelivery.view.MyButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.learning.app.eventdelivery.MainActivity.TAG;


public class SecondActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    @BindView(R.id.btn1)
    MyButton btn1;
    @BindView(R.id.root)
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        root.setOnTouchListener(this);
        root.setOnClickListener(this);

        btn1.setOnTouchListener(this);
        btn1.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(TAG, "OnTouchListener---" + event.getAction() + "---view:" + v);
        return false;
//        return true;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "OnClickListener---view:" + v);
    }

}
