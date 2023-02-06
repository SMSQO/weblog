package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentInfo {
    long id;
    BloggerInfo author;
    String content;

    String post;  // URL
    CommentInfo replyTo;
    boolean reviewPassed;
}
