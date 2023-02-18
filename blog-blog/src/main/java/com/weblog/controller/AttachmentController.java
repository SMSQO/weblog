package com.weblog.controller;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.exception.EmptyAttachmentException;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.service.AttachmentService;
import com.weblog.business.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/blogger/{uid}/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PermissionService permissionService;


    @GetMapping
    public AttachmentInfo[] getBloggerAttachments(
            @PathVariable("uid") long uid,
            int page,
            @RequestParam("perpage") int pageSize
    ) throws PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfBlogger(uid);
        return attachmentService.getBloggerAttachments(uid, page, pageSize);
    }

    @GetMapping("/{aid}")
    public AttachmentInfo getAttachmentInfo(
            @PathVariable("uid") long uid,
            @PathVariable("aid") long aid
    ) throws EntityNotFoundException, PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfAttachment(aid);
        return attachmentService.getAttachmentInfo(aid);
    }

    @PostMapping
    public long uploadAttachment(
            @PathVariable("uid") long uid,
            @RequestParam("name") String filename,
            @RequestParam("file") MultipartFile file
    ) throws PermissionDeniedException, NotLoggedInException, EmptyAttachmentException, IOException {
        permissionService.assertIsSelfBlogger(uid);
        if (filename == null || filename.isBlank()) {
            filename = file.getName();
        }
        if (file.isEmpty()) {
            throw new EmptyAttachmentException();
        }
        return attachmentService.saveAttachment(uid, filename, file);
    }

    @DeleteMapping("/{aid}")
    public void deleteAttachmentInfo(
            @PathVariable("uid") long uid,
            @PathVariable("aid") long aid
    ) throws EntityNotFoundException, PermissionDeniedException, NotLoggedInException {
        permissionService.assertIsSelfBlogger(uid);
        attachmentService.deleteAttachment(aid);
    }
}
