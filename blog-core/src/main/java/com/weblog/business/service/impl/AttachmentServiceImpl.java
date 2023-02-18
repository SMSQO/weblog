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


@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final static String ATTACHMENT_URL_PATTERN = "/attachment/blogger/%d/%s";

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public AttachmentInfo[] getBloggerAttachments(long uid, int page, int perpage) {
        return attachmentMapper.getBloggerAttachment(uid, page * perpage, perpage);
    }

    @Override
    public AttachmentInfo getAttachmentInfo(long aid) throws EntityNotFoundException {
        val ret = attachmentMapper.getAttachmentInfo(aid);
        if (ret == null) {
            throw new EntityNotFoundException(String.format("Attachment not found with aid = %d", aid));
        }
        return ret;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public long saveAttachment(long uid, String filename, MultipartFile file) throws IOException {
        val owner = new BloggerInfo();
        owner.setId(uid);
        int pos = filename.lastIndexOf('.');
        val suffix = pos == -1 ? "" : filename.substring(pos + 1);
        val attachInfo = new AttachmentInfo(
                0L,
                filename,
                suffix,
                String.format(ATTACHMENT_URL_PATTERN, uid, filename),
                owner,
                file.getSize()
        );

        // val path = new File(WebConfig.ATTACHMENT_REAL_PATH);
        val path = new File("/home/smsqo/attachment/");
        if (!path.exists()) {
            path.mkdirs();
        }

        file.transferTo(new File(path, filename));
        attachmentMapper.addAttachmentInfo(attachInfo);
        return attachInfo.getId();
    }

    @Override
    public void deleteAttachment(long aid) throws EntityNotFoundException {
        val ok = attachmentMapper.deleteAttachmentInfo(aid);
        if (ok == 0) {
            throw new EntityNotFoundException(String.format("Attachment not found with aid = %d", aid));
        }
    }
}
