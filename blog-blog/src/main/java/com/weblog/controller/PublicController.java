package com.weblog.controller;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.BlogService;
import com.weblog.business.service.PostService;
import com.weblog.business.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.HTML;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;


    //浏览者主页
    @GetMapping(value = "")
    public PostInfo[] home(){
//        PageHelper.startPage(10,10);
        PostInfo[] posts =postService.listRecommended();
        System.out.println(posts[0]);
//        System.out.println(blogs);
//        model.addAttribute(postService.listRecommended())
//        System.out.println(postService.listRecommended());
        return  posts;
    }

    //博主主页
    @GetMapping(value = "/{bid}")
    public ModelAndView home(@PathVariable("bid") Long bid,
                             @RequestParam("page") int page,
                           @RequestParam("perpage") int pageSize){
//        PageHelper.startPage(10,10);
        PostInfo[] posts =postService.listRecommended();
        ModelAndView mv = new ModelAndView();
        mv.addObject("posts",posts);
        TagInfo[] tags = tagService.getBloggerTags(bid,page,pageSize);
        mv.addObject("tags",tags);
        PostInfo[] postsLiked =postService.findLikedPosts(bid);
        mv.addObject("postliked",postsLiked);
        BlogInfo blogInfo = blogService.getBlogInfo(bid);
        mv.addObject("blogInfo",blogInfo);

        return  mv;
    }

    @GetMapping(value = "/post")
    public PostInfo viewBlog(@RequestParam("pid") int pid) throws EntityNotFoundException {

        return postService.getPostInfo(pid);
    }

    @GetMapping(value = "/search")
    public PostInfo[] searchBlog(@RequestParam("tags") String tags,
                                 @RequestParam("findname")   String findname){
        return postService.searchPosts(tags,findname);
    }


    @GetMapping("/{pid}/like")
    public PostInfo[] likeBlog(@PathVariable("pid") int pid){

        return postService.findLikedPosts(pid);
    }




    @GetMapping("/tag")
    public TagInfo[] getTagInfo(int page, @RequestParam("perpage") int pageSize) {
        return tagService.getPublicTags(page, pageSize);
    }
}
