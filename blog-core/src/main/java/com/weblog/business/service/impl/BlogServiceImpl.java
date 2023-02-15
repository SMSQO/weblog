package com.weblog.business.service.impl;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.BlogService;
import com.weblog.persistence.mapper.BlogMapper;
import com.weblog.persistence.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO getBlogInfo need to change how many blogs in one page
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public BlogInfo getBlogInfo(long bid, boolean selfWatch) throws EntityNotFoundException {
        BlogInfo blogInfo;
        if (selfWatch)
            blogInfo = blogMapper.getBlogByIdAll(bid);
        else
            blogInfo = blogMapper.getBlogByIdPublic(bid);
        if (blogInfo == null)
            throw new EntityNotFoundException(String.format("blog with id %d not found", bid));
        blogInfo.setBlogsUrl(String.format("/blog/%d/post?page=0&perpage=5", bid));
        return blogInfo;
    }

    @Override
    public PostInfo[] getBlogAllPost(boolean selfWatch, long bid, int page, int pageSize) {
        PostInfo[] postInfos;
        if (selfWatch)
            postInfos = postMapper.getBloggerAllPostInfo(bid, page * pageSize, pageSize);
        else
            postInfos = postMapper.getBloggerPublicPostInfo(bid, page * pageSize, pageSize);
        return postInfos;
    }
}
