package com.dome.pachong.infokafka.dao;

import com.dome.pachong.infokafka.entity.WebsiteTask;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WebsiteTaskDao extends JpaRepository<WebsiteTask, Integer> {
}
