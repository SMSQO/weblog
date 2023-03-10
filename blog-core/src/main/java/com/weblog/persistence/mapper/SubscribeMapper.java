package com.weblog.persistence.mapper;

import com.weblog.business.entity.BloggerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubscribeMapper {

    int subscribe(long publisher, long fan);

    int unsubscribe(long publisher, long fan);

    boolean subscribed(long publisher, long fan);

    boolean bothBloggerExists(long publisher, long fan);

    int getOnesFansCount(long publisher);

    BloggerInfo[] getOnesFans(long publisher);
}
