package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.SameContactException;
import com.weblog.business.service.BloggerService;
import com.weblog.persistence.mapper.BloggerMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.weblog.business.ConstantUtil.*;

@Slf4j
@Service
public class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Override
    public BloggerInfo getBloggerInfo(long uid) throws EntityNotFoundException {
        val blogger = bloggerMapper.getBloggerById(uid);
        if (blogger == null) {
            throw new EntityNotFoundException(String.format("blogger with id %d not found", uid));
        }
        return blogger;
    }

    @Override
    public void updateBloggerInfo(BloggerInfo info) throws EntityNotFoundException, SameContactException {
        final int ret;
        try {
            ret = bloggerMapper.updateBloggerInfo(info);
        } catch (DataIntegrityViolationException e) {
            throw new SameContactException(String.format("Someone else has the same concat: %s", info.getContact()));
        }
        if (ret != 1) {
            throw new EntityNotFoundException(String.format("blogger with id %d not found", info.getId()));
        }
    }

    @Override
    public long addBloggerInfo(BloggerInfo info) throws LoginRegisterException {
        try {
            bloggerMapper.addBloggerInfo(info);
        } catch (DuplicateKeyException e) {
            throw new LoginRegisterException("Another blogger with same contact has been registered.");
        }
        return info.getId();
    }

    @Override
    public String updateBloggerAvatar(long uid, MultipartFile file) throws IOException {
        val path = new File(AVATAR_REAL_PATH);
        if (!path.exists()) {
            //noinspection ResultOfMethodCallIgnored
            path.mkdirs();
        }
        val f = new File(path, String.valueOf(uid));
        file.transferTo(f);
        return String.format(AVATAR_URL_PATTERN, uid);
    }
}
