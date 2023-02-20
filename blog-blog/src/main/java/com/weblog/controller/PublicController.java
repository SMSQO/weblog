package com.weblog.controller;

import com.weblog.business.entity.PostInfo;
import com.weblog.business.entity.TagInfo;
import com.weblog.business.service.PostService;
import com.weblog.business.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;


    @GetMapping("/post")
    public PostInfo[] viewRecommendPosts(int page, @RequestParam("perpage") int pageSize) {
        return postService.listRecommended(page, pageSize);
    }

    @GetMapping("/search")
    public List<PostInfo> searchBlog(
            @RequestParam(value = "tags", required = false) String[] tags,
            @RequestParam(value = "info", required = false) String hint
    ) {
        tags = tags != null ? tags : new String[0];
        return postService.searchPosts(tags, hint);
    }


    @GetMapping("/{pid}/like")
    public void likeBlog(@PathVariable("pid") int pid, long bid) {
        // 只有登录的博主才能对博文进行喜欢（1次）
        postService.likePost(bid, pid);
    }


    @GetMapping("/tag")
    public TagInfo[] searchTagInfo(int page, @RequestParam("perpage") int pageSize) {
        return tagService.getPublicTags(page, pageSize);
    }

    @GetMapping("/tag/hot")
    public TagInfo[] getPublicHotTagInfo() {
        return tagService.getHotTags();
    }
}
