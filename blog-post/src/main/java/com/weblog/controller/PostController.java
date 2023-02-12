package com.weblog.controller;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.PostService;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/blog/{bid}/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public long addPost(
            @PathVariable("bid") long uid,
            @RequestBody PostInfo post
    ) {

        return postService.addPost(uid, post);
    }

    // 这个接口在博客管理部分也有写到.
    @GetMapping
    public PostInfo[] getBloggerPosts(
            @PathVariable("bid") long uid,
            int page,
            @RequestParam("perpage") int pageSize
    ) {
        return postService.getBloggerPosts(uid, page, pageSize);
    }

    @GetMapping("/{pid}")
    public PostInfo getPostInfo(
            @PathVariable("bid") long uid,
            @PathVariable("pid") long pid
    ) throws EntityNotFoundException {
        return postService.getPostInfo(pid);
    }

    @GetMapping("/{pid}/detail")
    public String getPostDetail(
            @PathVariable("bid") long uid,
            @PathVariable("pid") long pid
    ) throws EntityNotFoundException {
        return postService.getPostDetail(pid);
    }

    @PatchMapping("/{pid}")
    public void updatePostInfo(
            @PathVariable("bid") long uid,
            @PathVariable("pid") long pid,
            @RequestBody PostInfo post
    ) throws EntityNotFoundException {
        if (post.getBlogger() == null) {
            val blogger = new BloggerInfo();
            blogger.setId(uid);
            post.setBlogger(blogger);
        }
        post.setId(pid);
        postService.updatePostInfo(post);
    }

    @PatchMapping("/{pid}/detail")
    public void updatePostDetail(
            @PathVariable("bid") long uid,
            @PathVariable("pid") long pid,
            @RequestBody String detail
    ) throws EntityNotFoundException {
        log.info("OK, modifying details.");
        log.info(String.format("DETAIL = %s", detail));
        postService.updatePostDetail(pid, detail);
    }

    @DeleteMapping("/{pid}")
    public void deletePost(
            @PathVariable("bid") long uid,
            @PathVariable("pid") long pid
    ) throws EntityNotFoundException {
        postService.deletePost(pid);
    }

    @PostMapping("/test")
    public void test(
            @PathVariable("bid") long uid,
            @RequestBody String detail
    ) {
        log.info(String.format("detail = %s", detail));
    }


    @PatchMapping("/test")
    public void test2(
            @PathVariable("bid") long uid,
            @RequestBody String detail
    ) {
        log.info(String.format("detail = %s", detail));
    }
}
