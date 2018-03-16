package com.example.chenliang.test;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenliang.test.data_class.FirstPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenliang on 2017/12/14.
 */

public class Meinvzipai extends android.support.v4.app.Fragment {
    List<FirstPage> firstPageList=new ArrayList<>();
    String xingggan_mainpage_url;
    int lastvisibleItem;
    int count;
    List<FirstPage> onepagelist=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_xinggan,null);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.xingganRecyclerview);
        //final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        final Crawerrerer crawerrerer=new Crawerrerer();
        xingggan_mainpage_url="http://www.mzitu.com/taiwan/";
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                firstPageList=crawerrerer.get_data_of_first(xingggan_mainpage_url);
                System.out.println(firstPageList.size());
            }
        });
        t.start();try{t.join();}catch (InterruptedException e){e.printStackTrace();}
        final Recycler_adapter recycler_adapter=new Recycler_adapter(firstPageList);
        recyclerView.setAdapter(recycler_adapter);
        count=1;
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastvisibleItem+1==recycler_adapter.getItemCount()){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            count+=1;
                            final String newUrl=xingggan_mainpage_url+"page"+'/'+count+'/';
                            Thread thread=new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    onepagelist=crawerrerer.get_data_of_first(newUrl);
                                }
                            });
                            thread.start();try{thread.join();}catch (InterruptedException e){e.printStackTrace();}
                            firstPageList.addAll(onepagelist);
                            recycler_adapter.notifyDataSetChanged();
                        }
                    },500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastvisibleItem=gridLayoutManager.findLastVisibleItemPosition();
                //new LinearLayoutManager(view.getContext()).findLastVisibleItemPosition()
            }
        });

        return view;
    }
}

