package com.example.chenliang.test;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenliang on 2017/12/15.
 */

public class Recycler_sec_adapter extends RecyclerView.Adapter<Recycler_sec_adapter.ViewHolder> {
    List<String> urlList=new ArrayList<>();
    Context mcontext;

    float startx,starty,currentx,currenty;
    int position1;
    Recycler_sec_adapter(List<String> urllist1){
        urlList=urllist1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ViewHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.imageView_sec);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.recycler_sec_adapter,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUrl glideUrl=new GlideUrl(urlList.get(position),new LazyHeaders.Builder().addHeader("Referer",urlList.get(position)).build());
        Glide.with(mcontext).load(glideUrl).into(holder.imageView);
        final Dialog dialog=new Dialog(mcontext,android.R.style.Theme_Black_NoTitleBar);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.preview_image);
                final PinchImageView pinchImageView=(PinchImageView)dialog.findViewById(R.id.preview_imageview);

                final Button close_preview=(Button)dialog.findViewById(R.id.close_preview);
                close_preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                pinchImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                GlideUrl glideUrl=new GlideUrl(urlList.get(position),new LazyHeaders.Builder().addHeader("Referer",urlList.get(position)).build());
                Glide.with(mcontext).load(glideUrl).into(pinchImageView);
                position1=position;

               /* pinchImageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                startx=motionEvent.getRawX();
                                starty=motionEvent.getRawY();
                                break;
                            case MotionEvent.ACTION_MOVE:

                                break;
                            case MotionEvent.ACTION_UP:
                                currentx=motionEvent.getRawX();
                                currenty=motionEvent.getRawY();
                                System.out.println("position1"+position1);
                                if(currentx-startx>200){
                                    //显示上一个图片
                                    position1--;
                                    if(position1>=0){
                                        GlideUrl glideUrl=new GlideUrl(urlList.get(position1),new LazyHeaders.Builder().addHeader("Referer",urlList.get(position)).build());
                                        Glide.with(mcontext).load(glideUrl).into(pinchImageView);}
                                        else{position1=0;}
                                }
                                if (startx-currentx>200){
                                    position1++;
                                    if(position1<urlList.size()){
                                        GlideUrl glideUrl=new GlideUrl(urlList.get(position1),new LazyHeaders.Builder().addHeader("Referer",urlList.get(position)).build());
                                        Glide.with(mcontext).load(glideUrl).into(pinchImageView);}
                                        else {position1=(urlList.size()-1);}
                                }
                        }
                        return true;
                    }
                });*/
                dialog.show();
            }
        });

    }
    @Override
    public int getItemCount(){
        return urlList.size();
    }
}

