package com.weblog.business.service;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentService {

    AttachmentInfo[] getBloggerAttachments(long uid, int page, int perpage);

    AttachmentInfo getAttachmentInfo(long aid) throws EntityNotFoundException;

    long saveAttachment(long uid, String filename, MultipartFile file) throws IOException;

    void deleteAttachment(long aid) throws EntityNotFoundException;

    String getAttachmentMd5sum(long aid);
}
