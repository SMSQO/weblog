package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloggerInfo {
    long id;
    String name;
    String password;
    String avatarUrl; // URL
    String contact;
    String email;
    String graduate;

    public BloggerInfo(long uid) {
        this.id = uid;
    }
}
