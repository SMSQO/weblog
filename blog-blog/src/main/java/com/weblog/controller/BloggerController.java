package com.weblog.controller;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.*;
import com.weblog.business.service.BloggerService;
import com.weblog.business.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/blogger")
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/self")
    public BloggerInfo getSelfBloggerInfo() throws NotLoggedInException, EntityNotFoundException {
        val uid = permissionService.getSelfBloggerId();
        return bloggerService.getBloggerInfo(uid);
    }

    @GetMapping("/{uid}")
    public BloggerInfo getBloggerInfo(@PathVariable("uid") long uid) throws EntityNotFoundException {
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

    @PatchMapping("/{uid}/avatar")
    public String updateBloggerAvatar(
            @PathVariable("uid") long uid,
            @RequestParam("file") MultipartFile file
    ) throws PermissionDeniedException, NotLoggedInException, EmptyAttachmentException, IOException {
        permissionService.assertIsSelfBlogger(uid);
        if (file.isEmpty()) {
            throw new EmptyAttachmentException();
        }
        return bloggerService.updateBloggerAvatar(uid, file);
    }
}
