package com.example.chenliang.test;

import android.net.wifi.hotspot2.ConfigParser;

import com.example.chenliang.test.data_class.FirstPage;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


/**
 * Created by chenliang on 2017/12/12.
 */

public class Crawerrerer {

    public List<FirstPage> get_data_of_first(String url) {
       try{
           org.jsoup.nodes.Document doc=Jsoup.connect(url).get();
           System.out.println(doc.title());

           Element element=doc.getElementById("pins");
           Elements li=element.getElementsByTag("li");
           List<FirstPage> firstPageList=new ArrayList<>();
           for(Element litag:li){
               String fengmianUrl=litag.getElementsByTag("a").first().getElementsByTag("img").first().attr("data-original");
               String title=litag.getElementsByTag("a").first().getElementsByTag("img").first().attr("alt") ;
               String href=litag.getElementsByTag("a").first().attr("href");
               String time=litag.getElementsByTag("span").get(1).text();
               String viewerNum=litag.getElementsByTag("span").get(2).text();
               FirstPage f=new FirstPage();
               f.setFengmianUrl(fengmianUrl);
               f.setHref(href);
               f.setTime(time);
               f.setViewerNum(viewerNum);
               f.setTitle(title);

               firstPageList.add(f);
           }
           return firstPageList;
       }catch (IOException e){
           e.printStackTrace();
       }
       return null;
}

    public List<String> get_second_page_all_pics(String url){
        try{
            org.jsoup.nodes.Document doc=Jsoup.connect(url).get();
            String pageNum_String=doc.getElementsByAttributeValue("class","pagenavi").first().getElementsByTag("a").get(4).getElementsByTag("span")
                    .text();//获取总页数
            int pageNum=Integer.parseInt(pageNum_String);
            String firstpicurl=new String();
            Connection connection=Jsoup.connect(url).referrer(url).userAgent("Mozilla");
            Connection.Response response=connection.execute();
            System.out.println(response.statusCode());
            if (response.statusCode()==200){
                try{
                    org.jsoup.nodes.Document document=connection.get();
                    firstpicurl=document.getElementsByAttributeValue("class","main-image").first().getElementsByTag("p").first()
                            .getElementsByTag("a").first().getElementsByTag("img").first().attr("src");
                    System.out.println(firstpicurl);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            List<String> num=generateNum(pageNum);
            List<String > picUrlList=new ArrayList<>();
            for(String numm:num){
                picUrlList.add(generateUrl(firstpicurl,numm));
            }

            /*for(int i=1;i<pageNum+1;i++){
                String pageUrl=url+"/"+i;
                Connection connection=Jsoup.connect(pageUrl).referrer(pageUrl).userAgent("Mozilla");
                Connection.Response response=connection.execute();
                System.out.println(response.statusCode());
                if (response.statusCode()==200){
                try{
                    org.jsoup.nodes.Document document=connection.get();
                    String picurl=document.getElementsByAttributeValue("class","main-image").first().getElementsByTag("p").first()
                            .getElementsByTag("a").first().getElementsByTag("img").first().attr("src");
                    System.out.println(picurl);
                picUrlList.add(picurl);
                }catch (Exception e){
                    e.printStackTrace();
                }
                }
            }*/return picUrlList;


        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
}

    public List<String> generateNum(int maxNum){
        if(maxNum<100){
            List<String> generatedNum=new ArrayList<>();
            for(int i=1;i<=maxNum;i++){
                if(i<10){
                    generatedNum.add("0"+i);
                }
                else
                {
                    generatedNum.add(i+"");
                }
            }
            return generatedNum;
        }
        if(maxNum<1000&&maxNum>100){
            List<String> generatedNum=new ArrayList<>();
            for(int i=1;i<maxNum;i++){
                if(i<10){
                    generatedNum.add("00"+i);
                }
                if(i<100&&i>10)
                {
                    generatedNum.add("0"+i);
                }
                if(i>=100){
                    generatedNum.add(i+"");
                }
        }
        return generatedNum;
    }
    return null;
    }

    public String generateUrl(String firstUrl,String num){
        String a=firstUrl;
        String qian=a.substring(0,a.length()-6);
        String hou =a.substring(a.length()-4,a.length());
        //System.out.println(qian+"    "+hou);
        String b=qian +num+hou;
      //  System.out.println(b);
        return b;
    }


    public static void main(String[] args) {
        Crawerrerer crawerrerer=new Crawerrerer();
       List<String> a=crawerrerer.get_second_page_all_pics("http://www.mzitu.com/113200");
       for(String mmmm:a){
           System.out.println(mmmm);
       }


        }

    }
