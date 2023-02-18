package com.weblog.controller;

import com.weblog.business.service.AttachmentService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 这个Controller不是RESTful的, 它用于转发获取文件(包括附件和头像)的请求
 */
@Controller
public class FileController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/file/blogger/*/attachment/{aid}")
    public String getAttachmentFile(
            @PathVariable("aid") long aid
    ) {
        val md5 = attachmentService.getAttachmentMd5sum(aid);
        return String.format("/static/files/%s", md5);
    }

    @GetMapping("/file/blogger/{uid}/avatar")
    public String getAvatarFile(@PathVariable("uid") long uid) {
        return String.format("/static/avatars/%d", uid);
    }
}
