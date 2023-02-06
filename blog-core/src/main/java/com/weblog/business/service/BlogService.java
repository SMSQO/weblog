package com.weblog.business.service;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;

public interface BlogService {

    BlogInfo getBlogInfo(long bid);

    PostInfo[] getBlogAllPost(int page, int pageSize);
}
