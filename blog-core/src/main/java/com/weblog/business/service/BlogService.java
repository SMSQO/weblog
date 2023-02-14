package com.weblog.business.service;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;

public interface BlogService {

    BlogInfo getBlogInfo(long bid, long uid) throws EntityNotFoundException;

    PostInfo[] getBlogAllPost(long uid, long bid, int page, int pageSize);




}
