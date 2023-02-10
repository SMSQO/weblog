package com.weblog.controller;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.exception.ReviewException;
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

    @GetMapping("/{bid}/post/{pid}/comment")
    @NonNull
    public CommentInfo[] getCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, boolean all, int page, int perpage) {
        return blogService.getCommentInfo(bid, pid, all, page, perpage);
    }

    @PostMapping("/{bid}/post/{pid}/comment")
    @NonNull
    public void addCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, CommentInfo comment, long reply) {
        blogService.addCommentInfo(bid, pid, comment, reply);
    }

    @DeleteMapping("/{bid}/post/{pid}/comment/{cid}")
    @NonNull
    public void deleteCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid) throws DeleteException {
        blogService.deleteCommentInfo(bid, pid, cid);
    }

    @GetMapping("{bid}/post/{pid}/comment/{cid}/reply")
    @NonNull
    public CommentInfo[] getAllReplyComment(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid, int page, int perpage) {
        return blogService.getAllReplyComment(bid, pid, cid, page, perpage);
    }

    @PostMapping("{bid}/post/{pid}/comment/{cid}/review")
    @NonNull
    public void reviewComment(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid, boolean pass) throws ReviewException {
        blogService.reviewComment(bid, pid, cid, pass);
    }
}
