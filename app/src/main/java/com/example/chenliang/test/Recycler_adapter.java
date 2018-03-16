package com.example.chenliang.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.chenliang.test.data_class.FirstPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenliang on 2017/12/15.
 */

public class Recycler_adapter extends RecyclerView.Adapter<Recycler_adapter.ViewHolder> {
    private List<FirstPage> mFirstPageList=new ArrayList<>();
    Context mcontext;
    Recycler_adapter(List<FirstPage> firstPageList){
        mFirstPageList=firstPageList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView pic;
        View view1;
        ViewHolder(View view) {
            super(view);
            view1=view;
            title=(TextView)view.findViewById(R.id.textView);
            pic=(ImageView)view.findViewById(R.id.imageView);
        }
    }
    @Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        mcontext=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //super.onBindViewHolder(holder, position, payloads);
        final FirstPage content=mFirstPageList.get(position);
        holder.title.setText(content.getTitle());
        GlideUrl glideUrl=new GlideUrl(content.getFengmianUrl(),new LazyHeaders.Builder().addHeader("Referer",content.getFengmianUrl()).build());
        Glide.with(mcontext).load(glideUrl).into(holder.pic);
        holder.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstPage firstPage=mFirstPageList.get(position);
                Intent intent=new Intent(mcontext,Picshow.class);
                intent.putExtra("url",firstPage.getHref());
                intent.putExtra("title",firstPage.getTitle());
                mcontext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
    return mFirstPageList.size();
    }
}
