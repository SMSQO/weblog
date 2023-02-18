package com.weblog.controller;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.service.CommentService;
import com.weblog.business.service.PermissionService;
import com.weblog.business.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/blog/{bid}/post/{pid}/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @NonNull
    public CommentInfo[] getCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, boolean all, int page, int perpage) throws PermissionDeniedException {
        try {
            permissionService.assertIsSelfBlogger(bid);
        } catch (NotLoggedInException | PermissionDeniedException e) {
            throw new PermissionDeniedException();
        }
        return commentService.getCommentInfo(bid, pid, all, page, perpage);
    }

    @PostMapping
    @NonNull
    public void addCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, CommentInfo comment, long reply) throws NotLoggedInException {
        permissionService.getSelfBloggerId();
        commentService.addCommentInfo(bid, pid, comment, reply);
    }

    @DeleteMapping("/{cid}")
    @NonNull
    public void deleteCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid) {
        commentService.deleteCommentInfo(bid, pid, cid);
    }

    @GetMapping("/{cid}/reply")
    @NonNull
    public CommentInfo[] getAllReplyComment(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid, int page, int perpage) {
        return commentService.getAllReplyComment(bid, pid, cid, page, perpage);
    }

    @PostMapping("/{cid}/review")
    @NonNull
    public void reviewComment(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid, boolean pass) throws PermissionDeniedException, EntityNotFoundException, NotLoggedInException {
        if (permissionService.getSelfBloggerId() != postService.getPostInfo(pid).getBlogger().getId()) {
            throw new PermissionDeniedException();
        }
        commentService.reviewComment(bid, pid, cid, pass);
    }
}
