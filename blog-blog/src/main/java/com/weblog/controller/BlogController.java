package com.weblog.controller;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.service.BlogService;
import com.weblog.business.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/{bid}")
    @NonNull
    public BlogInfo getBloggerInfo(HttpServletRequest request, @PathVariable("bid") long bid) throws EntityNotFoundException {
        long uid;
        try {
            uid = permissionService.getSelfBloggerId();
        } catch (NotLoggedInException e) {
            uid = -1;
        }

        return blogService.getBlogInfo(bid, uid, request);
    }

    @GetMapping("/{bid}/post")
    @NonNull
    public PostInfo[] getBlogAllPost(@PathVariable("bid") long bid, int page, int perpage) throws NotLoggedInException {
        long uid;
        try {
            uid = permissionService.getSelfBloggerId();
        } catch (NotLoggedInException e) {
            uid = -1;
        }
        return blogService.getBlogAllPost(uid, bid, page, perpage);
    }
}
