package com.weblog.controller;

import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.service.PermissionService;
import com.weblog.business.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/blogger/{uid}/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public TagInfo[] getBloggerTags(
            @PathVariable("uid") long uid,
            int page,
            @RequestParam("perpage") int pageSize
    ) throws PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfBlogger(uid);
        return tagService.getBloggerTags(uid, page, pageSize);
    }

    @GetMapping("/{tid}")
    public TagInfo getTagInfo(
            @PathVariable("uid") long uid,
            @PathVariable("tid") long tid
    ) throws EntityNotFoundException, PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfBlogger(uid);
        return tagService.getTagInfo(tid);
    }

    @PostMapping
    public long addTag(@PathVariable("uid") long uid, @RequestBody TagInfo tag) throws PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfBlogger(uid);
        return tagService.addTag(tag);
    }

    @PatchMapping("/{tid}")
    public void updateTag(
            @PathVariable("uid") long uid,
            @PathVariable("tid") long tid,
            @RequestBody TagInfo tag
    ) throws EntityNotFoundException, PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfTag(tid);
        tag.setId(tid);
        tagService.updateTag(tag);
    }

    @DeleteMapping("/{tid}")
    public void deleteTag(
            @PathVariable("uid") long uid,
            @PathVariable("tid") long tid
    ) throws EntityNotFoundException, PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfTag(tid);
        tagService.deleteTag(tid);
    }
}
