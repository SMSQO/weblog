package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogInfo {
    BloggerInfo blogger;
    long visitCount;
    int likeCount;
    int fans;

    int blogCount;
    String blogsUrl;
}
