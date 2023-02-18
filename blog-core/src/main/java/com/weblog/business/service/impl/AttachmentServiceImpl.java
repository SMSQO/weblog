package com.weblog.business.service.impl;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.AttachmentService;
import com.weblog.persistence.mapper.AttachmentMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.weblog.business.ConstantUtil.*;


@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public AttachmentInfo[] getBloggerAttachments(long uid, int page, int perpage) {
        val ret = attachmentMapper.getBloggerAttachment(uid, page * perpage, perpage);
        for (val it : ret) {
            it.setUrl(String.format(ATTACHMENT_URL_PATTERN, it.getOwner().getId(), it.getId()));
        }
        return ret;
    }

    @Override
    public AttachmentInfo getAttachmentInfo(long aid) throws EntityNotFoundException {
        val ret = attachmentMapper.getAttachmentInfo(aid);
        if (ret == null) {
            throw new EntityNotFoundException(String.format("Attachment not found with aid = %d", aid));
        }
        ret.setUrl(String.format(ATTACHMENT_URL_PATTERN, ret.getOwner().getId(), aid));
        return ret;
    }

    @Override
    public long saveAttachment(long uid, String filename, MultipartFile file) throws IOException {
        val path = new File(ATTACHMENT_REAL_PATH);
        if (!path.exists()) {
            //noinspection ResultOfMethodCallIgnored
            path.mkdirs();
        }
        String md5 = getFileMD5(file);
        val f = new File(path, md5);
        if (!f.exists()) {
            file.transferTo(f);
        }

        val owner = new BloggerInfo();
        owner.setId(uid);
        int pos = filename.lastIndexOf('.');
        val suffix = pos == -1 ? "" : filename.substring(pos + 1);
        val attachInfo = new AttachmentInfo(0L, filename, suffix, "", owner, file.getSize());
        attachmentMapper.addAttachmentInfo(attachInfo, md5);
        return attachInfo.getId();
    }

    @Override
    public void deleteAttachment(long aid) throws EntityNotFoundException {
        val ok = attachmentMapper.deleteAttachmentInfo(aid);
        if (ok == 0) {
            throw new EntityNotFoundException(String.format("Attachment not found with aid = %d", aid));
        }
    }

    @Override
    public String getAttachmentMd5sum(long aid) {
        return attachmentMapper.getAttachmentMd5(aid);
    }

    private String getFileMD5(MultipartFile file) throws IOException {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException ignore) {
        }
        return ""; // Never happen
    }

}
