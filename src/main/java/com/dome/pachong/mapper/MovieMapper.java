package com.dome.pachong.mapper;

import com.dome.pachong.movel.Movie;

import java.util.List;

public interface MovieMapper {

    void insert(Movie movie);

    List<Movie> findAll();

    void update(Movie movie);

    Long findByid(String id);
}