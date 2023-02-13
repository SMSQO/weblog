package com.weblog.persistence.mapper;

import com.weblog.business.entity.BlogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogMapper {


    @Nullable
    BlogInfo getBlogById(long id);

}
