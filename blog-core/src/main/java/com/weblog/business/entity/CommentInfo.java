package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfo {
    long id;
    BloggerInfo author;
    String content;

    String post;    // URL
    String replied; // URL
    CommentInfo replyTo;
    boolean reviewPassed;
}
