package com.weblog.controller;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.exception.SameContactException;
import com.weblog.business.service.BloggerService;
import com.weblog.business.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/blogger")
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/self")
    private BloggerInfo getSelfBloggerInfo() throws NotLoggedInException {
        return permissionService.getSelfBloggerInfo();
    }

    @GetMapping("/{uid}")
    public BloggerInfo getBloggerInfo(@PathVariable("uid") long uid) {
        return bloggerService.getBloggerInfo(uid);
    }

    @PatchMapping("/{uid}")
    public void updateBloggerInfo(
            @PathVariable("uid") long uid,
            @RequestBody BloggerInfo info
    ) throws EntityNotFoundException, PermissionDeniedException, NotLoggedInException, SameContactException {
        permissionService.assertIsSelfBlogger(uid);
        info.setId(uid);
        bloggerService.updateBloggerInfo(info);
    }
}
