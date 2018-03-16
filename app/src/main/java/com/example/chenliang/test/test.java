package com.example.chenliang.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenliang on 2017/12/13.
 */

public class test {
    public static void main(String[] args) {
        String url="http://www.mzitu.com/112618/38";
        try{
        Document document= Jsoup.connect(url).get();
            String picurl=document.getElementsByAttributeValue("class","main-image").first().getElementsByTag("p").first()
                    .getElementsByTag("a").first().getElementsByTag("img").first().attr("src");
            System.out.println(picurl);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
