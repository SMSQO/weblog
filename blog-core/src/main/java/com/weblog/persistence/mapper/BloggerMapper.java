package com.weblog.persistence.mapper;

import com.weblog.business.entity.BloggerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BloggerMapper {

    int addBloggerInfo(BloggerInfo blogger) throws DuplicateKeyException;

    @Nullable
    BloggerInfo getBloggerById(long id);

    @Nullable
    BloggerInfo getBloggerByContactAndPassword(String contact, String password);

    int updateBloggerInfo(BloggerInfo info);
}
