package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloggerInfo {
    long id;
    String name;
    String avatarUrl; // URL
    String contact;
    String email;
    String graduate;
}
