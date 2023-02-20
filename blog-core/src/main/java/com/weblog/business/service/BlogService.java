package com.weblog.business.service;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;

public interface BlogService {

    BlogInfo getBlogInfo(long bid, boolean selfWatch) throws EntityNotFoundException;

    PostInfo[] getBlogAllPost(boolean selfWatch, long bid, int page, int pageSize);
}
