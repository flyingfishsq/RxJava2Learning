package com.learning.app.md_instagram;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseDrawerActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected ImageView ivLogo;

    private MenuItem inBoxMenuItem;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        setupToolbar();
    }

    protected void initView(){
        toolbar = findViewById(R.id.toolBar);
        ivLogo = findViewById(R.id.ivLogo);
    }

    protected void setupToolbar(){
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(android.R.drawable.ic_menu_more);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        inBoxMenuItem = menu.findItem(R.id.action_inbox);
//        inBoxMenuItem.setActionView(R.layout.menu_item_view);
        return true;
    }
}
