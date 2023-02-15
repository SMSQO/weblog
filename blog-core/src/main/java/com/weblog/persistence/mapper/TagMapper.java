package com.weblog.persistence.mapper;

import com.weblog.business.entity.TagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TagMapper {

    TagInfo  getTagInfoByName(String tags);

    TagInfo[] getPublicTags(int from, int count);

    TagInfo[] getBloggerTags(long uid, int from, int count);

    TagInfo getTagInfo(long tid);

    void addTag(TagInfo tag);

    int updateTag(TagInfo tag);

    int deleteTag(long tid);
}
