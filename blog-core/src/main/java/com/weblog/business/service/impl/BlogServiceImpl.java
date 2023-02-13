package com.weblog.business.service.impl;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.CommentInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.ReviewException;
import com.weblog.business.service.BlogService;
import com.weblog.persistence.mapper.BlogMapper;
import com.weblog.persistence.mapper.PostMapper;
import com.weblog.persistence.mapper.SubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

// TODO getBlogInfo need to change how many blogs in one page
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public BlogInfo getBlogInfo(long bid, long uid, HttpServletRequest request) throws EntityNotFoundException {
        boolean selfWatch = uid == bid;
        BlogInfo blogInfo = blogMapper.getBlogById(bid);
        if (blogInfo == null)
            throw new EntityNotFoundException(String.format("blog with id %d not found", bid));
        blogInfo.setFans(subscribeMapper.getOnesFans(bid));
        PostInfo[] postInfos;
        if (selfWatch)
            postInfos = postMapper.getBloggerAllPostInfo(bid, 0, 0);
        else
            postInfos = postMapper.getBloggerPublicPostInfo(bid, 0, 0);
        blogInfo.setBlogCount(postInfos.length);
        blogInfo.setBlogsUrl(request.getRequestURL() + "/post?page=0&perpage=5");
        return blogInfo;
    }

    @Override
    public PostInfo[] getBlogAllPost(long uid, long bid, int page, int pageSize) {
        boolean selfWatch = uid == bid;
        PostInfo[] postInfos;
        ArrayList<PostInfo> blogs = new ArrayList<>();
        if (selfWatch)
            postInfos = postMapper.getBloggerAllPostInfo(bid, 0, 0);
        else
            postInfos = postMapper.getBloggerPublicPostInfo(bid, 0, 0);
        for (int i = 0; i < postInfos.length; i++) {
            if (i >= page * pageSize && i < (page + 1) * pageSize) {
                blogs.add(postInfos[i]);
            }
        }
        postInfos = null;
        if (blogs.size() > 0)
            postInfos = blogs.toArray(new PostInfo[0]);
        return postInfos;
    }

    @Override
    public CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize) {
        return new CommentInfo[0];
    }

    @Override
    public void addCommentInfo(long bid, long pid, CommentInfo comment, long reply) {

    }

    @Override
    public void deleteCommentInfo(long bid, long pid, long cid) throws DeleteException {

    }

    @Override
    public CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize) {
        return new CommentInfo[0];
    }

    @Override
    public void reviewComment(long bid, long pid, long cid, boolean pass) throws ReviewException {

    }
}
