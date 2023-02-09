package com.weblog.controller;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

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
        return null; // TODO
    }

    @GetMapping("/{aid}")
    public AttachmentInfo getAttachmentInfo(
            @PathVariable("uid") long uid,
            @PathVariable("aid") long aid
    ) {
        return null; // TODO
    }

    @PostMapping
    public long addAttachmentInfo(
            @PathVariable("uid") long uid,
            @RequestParam("name") String filename,
            File file // TODO
    ) {
        return 0L; // TODO
    }

    @DeleteMapping("/{aid}")
    public void deleteAttachmentInfo(
            @PathVariable("uid") long uid,
            @PathVariable("aid") long aid
    ) {
        // TODO
    }
}
