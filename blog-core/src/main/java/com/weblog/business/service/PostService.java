package com.weblog.business.service;

import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;

public interface PostService {

    long addPost(long uid, PostInfo post);

    void updatePostInfo(PostInfo post) throws EntityNotFoundException;

    void updatePostDetail(long pid, String detail) throws EntityNotFoundException;

    void deletePost(long pid) throws EntityNotFoundException;


    PostInfo[] getBloggerPosts(long uid, int page, int pageSize);

    PostInfo getPostInfo(long pid) throws EntityNotFoundException;

    String getPostDetail(long pid) throws EntityNotFoundException;

    void updateAddPostLikes(long pid, int likes) throws EntityNotFoundException;

    void updateAddPostView(long pid, int views) throws EntityNotFoundException;
}
