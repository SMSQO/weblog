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

        return postService.listRecommended(page, pageSize);
    }

    @GetMapping(value = "/search")
    public Set<PostInfo> searchBlog(@RequestParam("tags") String[] tags,
                                    @RequestParam("findname") String findname) throws EntityNotFoundException {
        return postService.searchPosts(tags, findname);
    }


    @GetMapping("/{pid}/like")
    public void likeBlog(@PathVariable("pid") int pid, long bid) throws EntityNotFoundException {
        //只有登录的博主才能对博文进行喜欢（1次）
        postService.likePost(bid, pid);
    }


    @GetMapping("/tag")
    public TagInfo[] searchTagInfo(int page, @RequestParam("perpage") int pageSize) {
        return tagService.getPublicTags(page, pageSize);
    }

    @GetMapping("/tag/hot")
    public TagInfo[] getPublicHotTagInfo() {
        return new TagInfo[]{
                new TagInfo(1L, "公开的", null, "描述"),
                new TagInfo(2L, "热门热门标签", null, "描述"),
                new TagInfo(3L, "功能", null, "描述"),
                new TagInfo(4L, "还没有完成", null, "描述"),
                new TagInfo(6L, "这里的都是", null, "描述"),
                new TagInfo(7L, "假数据", null, "描述"),
                new TagInfo(8L, "前端注意", null, "描述"),
                new TagInfo(9L, "有可能返回", null, "描述"),
                new TagInfo(10L, "不到10个标签", null, "描述"),
        };
    }
}
