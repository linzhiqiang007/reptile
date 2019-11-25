package com.dome.pachong.infokafka.controller;

import com.dome.pachong.infokafka.dao.WebsiteTaskDao;
import com.dome.pachong.infokafka.entity.UrlTask;
import com.dome.pachong.infokafka.entity.WebsiteTask;
import com.dome.pachong.infokafka.kafka.TaskProducer;
import com.dome.pachong.infokafka.service.WebsiteTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
public class CrawlerTaskController {

    @Autowired
    private WebsiteTaskService websiteTaskService;

    @Autowired
    private WebsiteTaskDao websiteTaskDao;

    @Autowired
    private TaskProducer taskProducer;

    @PostMapping("task/add")
    @ResponseBody
    public Map<String, Object> addWebsiteTask(WebsiteTask item) {
        item.setTaskCount(1);
        websiteTaskService.put(item);
        UrlTask task = new UrlTask();
        task.setUrl(item.getUrl());
        task.setParentId(-1);
        task.setRootId(task.getRootId());
        task.setLevel(0);
        taskProducer.sendUrlTask(task);
        Map<String, Object> map = new HashMap<>();
        map.put("id", item.getId());
        map.put("message", "爬虫任务添加成功！");
        return map;
    }

    @PostMapping("task/get")
    @ResponseBody
    public WebsiteTask getWebsiteTask(int id) {
        //return websiteTaskDao.findById(id).get();
        return null;
    }
}
