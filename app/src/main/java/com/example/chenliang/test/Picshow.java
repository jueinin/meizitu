package com.example.chenliang.test;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Picshow extends AppCompatActivity {
  List<String> each_pic_url;
    int lastVisibleItem;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picshow);
        initdata();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_sec);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Recycler_sec_adapter recycler_sec_adapter=new Recycler_sec_adapter(each_pic_url);
        recyclerView.setAdapter(recycler_sec_adapter);
    }
   public void initdata(){
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        System.out.println(url);
        String title=intent.getStringExtra("title");
        System.out.println(title);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_sec);            //设置名字
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final Crawerrerer crawerrerer=new Crawerrerer();

       Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                each_pic_url=crawerrerer.get_second_page_all_pics(url);
            }
        });
       thread.start();
       try{
           thread.join();
       }catch (InterruptedException e){e.printStackTrace();
    }

}



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();break;
                default:break;
        }
        return true;
    }
}
