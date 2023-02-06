package com.weblog.controller;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.BloggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/blogger")
public class BloggerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BloggerService bloggerService;

    @GetMapping("/self")
    private BloggerInfo getSelfBloggerInfo() {
        return bloggerService.getSelfBloggerInfo();
    }

    @GetMapping("/{uid}")
    @NonNull
    public BloggerInfo getBloggerInfo(@PathVariable("uid") long uid) {
        return bloggerService.getBloggerInfo(uid);
    }

    @PatchMapping("/{uid}")
    public void updateBloggerInfo(@PathVariable("uid") long uid, BloggerInfo info) throws EntityNotFoundException {
        info.setId(uid);
        bloggerService.updateBloggerInfo(info);
    }
}
