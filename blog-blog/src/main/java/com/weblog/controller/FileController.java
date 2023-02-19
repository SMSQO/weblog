package com.weblog.controller;

import com.weblog.business.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;

import static com.weblog.business.ConstantUtil.AVATAR_REAL_PATH;

/**
 * 这个Controller不是RESTful的, 它用于转发获取文件(包括附件和头像)的请求
 */
@Slf4j
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

    @SuppressWarnings("SpringMVCViewInspection")
    @GetMapping("/file/blogger/{uid}/avatar")
    public String getAvatarFile(@PathVariable("uid") long uid) {
        val f = new File(AVATAR_REAL_PATH, String.valueOf(uid));
        return f.exists() ?
                String.format("/static/avatars/%d", uid) :
                "/static/avatars/default";
    }
}
