package com.weblog.persistence;

import com.weblog.business.BloggerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BloggerMapper {
     BloggerInfo queryOne();
}
