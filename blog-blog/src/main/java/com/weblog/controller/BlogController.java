package com.weblog.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;

    @GetMapping("/{bid}")
    @NonNull
    public BlogInfo getBloggerInfo(@PathVariable("bid") long bid) {
        return blogService.getBlogInfo(bid);
    }

    @GetMapping("/{bid}/post")
    @NonNull
    public PostInfo[] getBlogAllPost(@PathVariable("bid") long bid, int page, int perpage) {
        return blogService.getBlogAllPost(bid, page, perpage);
    }
}
