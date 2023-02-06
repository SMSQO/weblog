package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogInfo {
    BloggerInfo blogger;
    long visitCount;
    int likeCount;
    int fans;

    int blogCount;
    String blogsUrl;
}
