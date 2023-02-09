package com.weblog.controller;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


// TODO SECURITY CHECK REQUIRED.
@RestController
@RequestMapping("/blogger/{uid}/attachment")
public class AttachmentController {


    @Autowired
    private AttachmentService attachmentService;

    @GetMapping
    public AttachmentInfo[] getBloggerAttachments(
            @PathVariable("uid") long uid,
            int page,
            @RequestParam("perpage") int pageSize
    ) {
        return attachmentService.getBloggerAttachments(uid, page, pageSize);
    }

    @GetMapping("/{aid}")
    public AttachmentInfo getAttachmentInfo(
            @PathVariable("uid") long uid,
            @PathVariable("aid") long aid
    ) throws EntityNotFoundException {
        return attachmentService.getAttachmentInfo(aid);
    }

    @PostMapping
    public long uploadAttachment(
            @PathVariable("uid") long uid,
            @RequestParam("name") String filename,
            MultipartFile file
    ) {
        if (filename == null || filename.isBlank()) {
            filename = file.getName();
        }
        return attachmentService.saveAttachment(uid, filename, file);
    }

    @DeleteMapping("/{aid}")
    public void deleteAttachmentInfo(
            @PathVariable("uid") long uid,
            @PathVariable("aid") long aid
    ) throws EntityNotFoundException {
        attachmentService.deleteAttachment(aid);
    }
}
