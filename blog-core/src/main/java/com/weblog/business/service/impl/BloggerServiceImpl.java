package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.BloggerService;
import com.weblog.persistence.mapper.BloggerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Override
    public BloggerInfo getBloggerInfo(long uid) {
        return bloggerMapper.getBloggerById(uid);
    }

    @Override
    public BloggerInfo getSelfBloggerInfo() {
        return null; // TODO NOT FINISHED YET
    }

    @Override
    public void updateBloggerInfo(BloggerInfo info) throws EntityNotFoundException {
        final int ret = bloggerMapper.updateBloggerInfo(info);
        if (ret != 1) {
            throw new EntityNotFoundException(String.format("blogger with id %d not found", info.getId()));
        }
    }
}
