package com.weblog.business.service;

import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;

public interface TagService {

    TagInfo[] getPublicTags(int page, int pageSize);

    TagInfo[] getBloggerTags(long bloggerId, int page, int pageSize);

    TagInfo getTagInfo(long tid) throws EntityNotFoundException;


    long addTag(TagInfo tag);

    void updateTag(TagInfo tag) throws EntityNotFoundException;

    void deleteTag(long tid) throws EntityNotFoundException;
}
