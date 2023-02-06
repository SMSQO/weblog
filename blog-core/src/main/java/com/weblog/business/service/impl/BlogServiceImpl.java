package com.weblog.business.service.impl;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.service.BlogService;
import org.springframework.stereotype.Service;

// TODO
@Service
public class BlogServiceImpl implements BlogService {

    @Override
    public BlogInfo getBlogInfo(long bid) {
        return null;
    }

    @Override
    public PostInfo[] getBlogAllPost(int page, int pageSize) {
        return new PostInfo[0];
    }
}
