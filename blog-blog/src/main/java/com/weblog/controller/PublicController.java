package com.weblog.controller;

import com.weblog.business.entity.TagInfo;
import com.weblog.business.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    public TagInfo[] getTagInfo(int page, @RequestParam("perpage") int pageSize) {
        return tagService.getPublicTags(page, pageSize);
    }
}
