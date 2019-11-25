package com.dome.pachong.infokafka.dao;

import com.dome.pachong.infokafka.entity.UrlTask;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UrlTaskDao extends JpaRepository<UrlTask, Integer> {
}
