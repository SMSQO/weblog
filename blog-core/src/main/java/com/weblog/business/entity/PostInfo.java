package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostInfo {
    long id;

    String title;
    String content; // 用于粗略展示此博文的内容.
    String detail;  // 这是URL. 访问此URL以获得文章详情.

    BloggerInfo blogger;
    TagInfo[] tags;
    String avatar;
    String comments; // URL

    PostPermission permission;

    long visits;
    int likes;
    int unreviewedCount;

    @Data
    @AllArgsConstructor
    static public class PostPermission {
        boolean isPublic;
        boolean needReviewComment;
    }
}
