package com.weblog.persistence.mapper;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.BloggerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogMapper {


    @Nullable
    BlogInfo getBlogById(long id);

}
