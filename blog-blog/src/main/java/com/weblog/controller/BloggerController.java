package com.weblog.controller;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.service.BloggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/blogger")
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @GetMapping("/self")
    private BloggerInfo getSelfBloggerInfo() throws NotLoggedInException {
        return bloggerService.getSelfBloggerInfo();
    }

    @GetMapping("/{uid}")
    public BloggerInfo getBloggerInfo(@PathVariable("uid") long uid) {
        return bloggerService.getBloggerInfo(uid);
    }

    @PatchMapping("/{uid}")
    public void updateBloggerInfo(@PathVariable("uid") long uid, BloggerInfo info) throws EntityNotFoundException {
        info.setId(uid);
        bloggerService.updateBloggerInfo(info);
    }
}
