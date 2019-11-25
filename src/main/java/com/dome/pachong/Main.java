package com.dome.pachong;

import com.alibaba.fastjson.JSON;
import com.dome.pachong.mapper.MovieMapper;
import com.dome.pachong.movel.Movie;
import com.dome.pachong.utile.GetJson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static  ArrayList<Movie> movies = new ArrayList<>();

    public static  void  main(String [] args) {

        //视频
        //movie();

        //搜索内容
        String select_name = "奇幻";
        //排序方式
        String sort = "U";
        //查询数量
        int end = 1000;
        search_movie(select_name,sort,end);


    }

    public static void movie(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        MovieMapper movieMapper = sqlSession.getMapper(MovieMapper.class);

        int start;
        int total = 0;
        int end = 600;
        for (start  = 0; start <= end; start += 20)  {
            try {

                String address = "https://Movie.douban.com/j/new_search_subjects?sort="+"U"+"&range=0,10&tags="+""+"&start=" + start;

                JSONObject dayLine = new GetJson().getHttpJson(address,1);

                System.out.println("start:" + start);
                JSONArray json = dayLine.getJSONArray("data");
                List<Movie> list = JSON.parseArray(json.toString(), Movie.class);

                System.out.println(list);

                if (start == end){
                    System.out.println("已经爬取到底了");
                    sqlSession.close();
                }

                for (Movie movie : list) {
                    Long byid = movieMapper.findByid(movie.getId());
                    if (byid>0){
                    }else {
                        movieMapper.insert(movie);
                        sqlSession.commit();
                    }
                }
                total += list.size();
                System.out.println("正在爬取中---共抓取:" + total + "条数据");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void search_movie(String select_name,String sort,int end){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        MovieMapper movieMapper = sqlSession.getMapper(MovieMapper.class);

        int start;
        int total = 0;
        for (start  = 0; start <= end; start += 20)  {
            try {
                String address = "https://Movie.douban.com/j/new_search_subjects?sort="+sort+"&range=0,10&tags="+select_name+"&start=" + start;
                JSONObject dayLine = new GetJson().getHttpJson(address,1);

                System.out.println("start:" + start);
                JSONArray json = dayLine.getJSONArray("data");
                List<Movie> list = JSON.parseArray(json.toString(), Movie.class);

                //System.out.println(list);

                if (start == end){
                    System.out.println("已经爬取到底了");
                    System.out.println("======================================");
                    for (Movie movie : movies) {
                        System.out.println("电影名称：" + movie.getTitle()+", 电影图片："+movie.getCover() + ", 演员："+movie.getCasts()+",导演："+movie.getDirectors());
                    }
                }
                for (Movie movie : list) {
                    Long byid = movieMapper.findByid(movie.getId());
                    if (byid>0){
                    }else {
                        movies.add(movie);
                    }
                }
                total += list.size();
                System.out.println("正在爬取中---共抓取:" + total + "条数据");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static String StringReplace(String str){
        str = str.substring(1,str.length()-1);
        return str;
    }
}