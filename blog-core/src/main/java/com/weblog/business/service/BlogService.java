package com.weblog.business.service;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.CommentInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.ReviewException;

import javax.servlet.http.HttpServletRequest;

public interface BlogService {

    BlogInfo getBlogInfo(long bid, long uid, HttpServletRequest request) throws EntityNotFoundException;

    PostInfo[] getBlogAllPost(long uid, long bid, int page, int pageSize);




}
