package com.weblog.controller;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.exception.*;
import com.weblog.business.service.CommentService;
import com.weblog.business.service.PermissionService;
import com.weblog.business.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blog")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/{bid}/post/{pid}/comment")
    @NonNull
    public CommentInfo[] getCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, boolean all, int page, int perpage) {
        return commentService.getCommentInfo(bid, pid, all, page, perpage);
    }

    @PostMapping("/{bid}/post/{pid}/comment")
    @NonNull
    public void addCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, CommentInfo comment, long reply) throws NotLoggedInException {
        permissionService.getSelfBloggerId();
        commentService.addCommentInfo(bid, pid, comment, reply);
    }

    @DeleteMapping("/{bid}/post/{pid}/comment/{cid}")
    @NonNull
    public void deleteCommentInfo(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid) throws DeleteException {
        commentService.deleteCommentInfo(bid, pid, cid);
    }

    @GetMapping("{bid}/post/{pid}/comment/{cid}/reply")
    @NonNull
    public CommentInfo[] getAllReplyComment(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid, int page, int perpage) {
        return commentService.getAllReplyComment(bid, pid, cid, page, perpage);
    }

    @PostMapping("{bid}/post/{pid}/comment/{cid}/review")
    @NonNull
    public void reviewComment(@PathVariable("bid") long bid, @PathVariable("pid") long pid, @PathVariable("cid") long cid, boolean pass) throws PermissionDeniedException, EntityNotFoundException, NotLoggedInException {
        if(permissionService.getSelfBloggerId() != postService.getPostInfo(pid).getBlogger().getId()){
            throw new PermissionDeniedException();
        }
        commentService.reviewComment(bid, pid, cid, pass);
    }
}
