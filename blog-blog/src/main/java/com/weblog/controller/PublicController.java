package com.weblog.controller;

import com.weblog.business.entity.PostInfo;
import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.PostService;
import com.weblog.business.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;


    @GetMapping(value = "/post")
    public PostInfo[] viewRecommendPosts(int page, @RequestParam("perpage") int pageSize) throws EntityNotFoundException {

        return postService.listRecommended(page,pageSize);
    }

    @GetMapping(value = "/search")
    public Set<PostInfo> searchBlog(@RequestParam("tags") String[] tags,
                                    @RequestParam("findname")   String findname) throws EntityNotFoundException {
        return postService.searchPosts(tags,findname);
    }


    @GetMapping("/{pid}/like")
    public void likeBlog(@PathVariable("pid") int pid, long  bid)throws EntityNotFoundException{
        //只有登录的博主才能对博文进行喜欢（1次）
        postService.likePost(bid,pid);
    }


    @GetMapping("/tag")
    public TagInfo[] getTagInfo(int page, @RequestParam("perpage") int pageSize) {
        return tagService.getPublicTags(page, pageSize);
    }
}
