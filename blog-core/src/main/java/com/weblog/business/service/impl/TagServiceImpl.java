package com.weblog.business.service.impl;

import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.TagService;
import com.weblog.persistence.mapper.TagMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public TagInfo[] getPublicTags(int page, int pageSize) {
        return tagMapper.getPublicTags(page * pageSize, pageSize);
    }

    @Override
    public TagInfo[] getBloggerTags(long bloggerId, int page, int pageSize) {
        return tagMapper.getBloggerTags(bloggerId, page * pageSize, pageSize);
    }

    @Override
    public TagInfo getTagInfo(long tid) throws EntityNotFoundException {
        val tag = tagMapper.getTagInfo(tid);
        if (tag == null) {
            throw new EntityNotFoundException(String.format("TagInfo not found for tid = %s", tid));
        }
        return tag;
    }

    @Override
    public long addTag(TagInfo tag) {
        tagMapper.addTag(tag);
        return tag.getId();
    }

    @Override
    public void updateTag(TagInfo tag) throws EntityNotFoundException {
        int ok = tagMapper.updateTag(tag);
        if (ok == 0) {
            throw new EntityNotFoundException(String.format("TagInfo not found for tid = %s", tag.getId()));
        }
    }

    @Override
    public void deleteTag(long tid) throws EntityNotFoundException {
        int ok = tagMapper.deleteTag(tid);
        if (ok == 0) {
            throw new EntityNotFoundException(String.format("TagInfo not found for tid = %s", tid));
        }
    }
}
