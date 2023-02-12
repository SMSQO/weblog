package com.weblog.persistence.mapper;

import com.weblog.business.entity.PostInfo;
import com.weblog.business.entity.TagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {

    PostInfo[] getBloggerAllPostInfo(long uid, int start, int count);

    PostInfo[] getBloggerPublicPostInfo(long uid, int start, int count);

    TagInfo[] getPostTags(long pid);

    PostInfo.PostPermission getPostPermission(long pid);

    PostInfo getPostInfo(long pid);

    String getPostDetail(long pid);

    long addPost(PostInfo post);

    // 除这些之外的字段都会被忽略: title, content, blogger, avatar, permission
    int updatePostInfo(PostInfo post);

    int updatePostTags(long pid, List<TagInfo> tags);

    int updatePostDetail(long pid, String detail);

    int deletePost(long pid);

    // 增加某帖子的喜欢数量
    int updateAddPostLike(long pid, int likes);

    int updateAddPostView(long pid, int views);
}
