package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentInfo {
    long id;
    String name;
    String url; // URL
    BloggerInfo owner;
    long filesize;
}
