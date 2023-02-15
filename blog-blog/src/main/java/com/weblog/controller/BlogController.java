package com.weblog.controller;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.service.BlogService;
import com.weblog.business.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/{bid}")
    @NonNull
    public BlogInfo getBloggerInfo(@PathVariable("bid") long bid) throws EntityNotFoundException {
        boolean selfBlog = true;
        try {
            permissionService.assertIsSelfBlogger(bid);
        } catch (PermissionDeniedException | NotLoggedInException e) {
            selfBlog = false;
        }
        return blogService.getBlogInfo(bid, selfBlog);
    }

    @GetMapping("/{bid}/post")
    @NonNull
    public PostInfo[] getBlogAllPost(@PathVariable("bid") long bid, int page, int perpage) {
        boolean selfBlog = true;
        try {
            permissionService.assertIsSelfBlogger(bid);
        } catch (PermissionDeniedException | NotLoggedInException e) {
            selfBlog = false;
        }
        return blogService.getBlogAllPost(selfBlog, bid, page, perpage);
    }
}
