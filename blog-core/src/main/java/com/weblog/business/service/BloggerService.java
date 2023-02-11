package com.weblog.business.service;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;

public interface BloggerService {

    BloggerInfo getBloggerInfo(long uid);

    BloggerInfo getSelfBloggerInfo();

    long getSelfBloggerId();

    void updateBloggerInfo(BloggerInfo bloggerinfo) throws EntityNotFoundException;

}
