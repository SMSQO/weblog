package com.weblog.controller;

import com.weblog.business.BloggerInfo;
import com.weblog.persistence.BloggerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/blogger")
public class BloggerController {

    @Autowired
    private BloggerMapper mapper;

    @GetMapping("/{uid}")
    @NonNull
    public BloggerInfo getBloggerInfo(@PathVariable("uid") long uid) {
        BloggerInfo ret =  mapper.queryOne();
        if (ret == null) {
            ret = new BloggerInfo(uid, "UmiKaiyo");
        }
        return ret; // just for testing
    }
}
