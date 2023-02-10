package com.weblog.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfo {
    long id;

    String title;
    String content; // 用于粗略展示此博文的内容.
    String detail;  // 这是URL. 访问此URL以获得文章详情.

    BloggerInfo blogger;
    List<TagInfo> tags;
    String avatar;
    String comments; // URL

    PostPermission permission;

    long visits;
    int likes;
    int unreviewedCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class PostPermission {
        @JsonProperty("isPublic")
        boolean isPublic;
        boolean needReviewComment;
    }
}
