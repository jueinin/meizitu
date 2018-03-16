package com.example.chenliang.test;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.chenliang.test.data_class.FirstPage;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationBar mbottomNavigationBar;
    NavigationView navigationView;
    private Meinvzipai meinvzipai;
    private Xingganmeinv xingganmeinv;
    private Qingchunmeinv qingchunmeinv;
    DrawerLayout drawerLayout;
   // List<FirstPage> firstPagedata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.cycle);
        }
        setDefaultfragment();
        initNavigationbar();
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.about:
                        Intent intent=new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                }
                return true;
            }
        });
    }

    public void setDefaultfragment(){
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(xingganmeinv==null){
        xingganmeinv=new Xingganmeinv();}
        fragmentTransaction.replace(R.id.fragment_container,xingganmeinv);
        fragmentTransaction.commit();

    }
    public void initNavigationbar(){
        mbottomNavigationBar=(BottomNavigationBar)findViewById(R.id.buttom_navigationbar);
        mbottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mbottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mbottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_launcher_foreground,"性感美女"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background,"清纯美女"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_foreground,"长腿美女")).setActiveColor(R.color.blackColor)
                .setFirstSelectedPosition(0)
                .initialise();
        mbottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                switch (position){
                    case 0:
                        if(xingganmeinv==null){
                            xingganmeinv=new Xingganmeinv();}
                        fragmentTransaction.replace(R.id.fragment_container,xingganmeinv);
                        break;
                    case 1:
                        if(qingchunmeinv==null){
                            qingchunmeinv=new Qingchunmeinv();
                        }fragmentTransaction.replace(R.id.fragment_container,qingchunmeinv);
                        break;
                    case 2:
                        if(meinvzipai==null){
                            meinvzipai=new Meinvzipai();
                        }
                        fragmentTransaction.replace(R.id.fragment_container,meinvzipai);
                    default:break;
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}

