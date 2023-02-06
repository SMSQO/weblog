package com.weblog.persistence.mapper;

import com.weblog.business.entity.BloggerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BloggerMapper {

    BloggerInfo getBloggerById(long id);

    int updateBloggerInfo(BloggerInfo info);
}
