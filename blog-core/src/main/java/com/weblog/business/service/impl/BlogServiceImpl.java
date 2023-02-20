package com.weblog.business.service.impl;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.BlogService;
import com.weblog.persistence.mapper.BlogMapper;
import com.weblog.persistence.mapper.PostMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.weblog.business.ConstantUtil.BLOGS_URL_PATTERN;

// TODO getBlogInfo need to change how many blogs in one page
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public BlogInfo getBlogInfo(long bid, boolean selfWatch) throws EntityNotFoundException {
        val blogInfo = selfWatch ?
                blogMapper.getBlogByIdAll(bid) :
                blogMapper.getBlogByIdPublic(bid);
        if (blogInfo == null) {
            throw new EntityNotFoundException(String.format("blog with id %d not found", bid));
        }
        blogMapper.updateBlogVisitCount(bid, 1);
        blogInfo.setBlogsUrl(String.format(BLOGS_URL_PATTERN, bid));
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
