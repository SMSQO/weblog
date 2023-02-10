package com.weblog.controller;

import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 *  TODO : Most methods in this controller needs security check.
 *   Add such code, plz.
 * */
@RestController
@RequestMapping("/blogger/{uid}/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    private final static Logger logger = LoggerFactory.getLogger(TagController.class);

    @GetMapping
    public TagInfo[] getBloggerTags(
            @PathVariable("uid") long uid,
            int page,
            @RequestParam("perpage") int pageSize
    ) {
        return tagService.getBloggerTags(uid, page, pageSize);
    }

    @GetMapping("/{tid}")
    public TagInfo getTagInfo(
            @PathVariable("uid") long uid,
            @PathVariable("tid") long tid
    ) throws EntityNotFoundException {
        return tagService.getTagInfo(tid);
    }

    @PostMapping
    public long addTag(@PathVariable("uid") long uid, @RequestBody TagInfo tag) {
        logger.info(tag.toString());
        return tagService.addTag(tag);
    }

    @PatchMapping("/{tid}")
    public void updateTag(
            @PathVariable("uid") long uid,
            @PathVariable("tid") long tid,
            @RequestBody TagInfo tag
    ) throws EntityNotFoundException {
        tag.setId(tid);
        tagService.updateTag(tag);
    }

    @DeleteMapping("/{tid}")
    public void deleteTag(
            @PathVariable("uid") long uid,
            @PathVariable("tid") long tid
    ) throws EntityNotFoundException {
        tagService.deleteTag(tid);
    }
}
