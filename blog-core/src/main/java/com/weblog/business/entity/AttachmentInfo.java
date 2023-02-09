package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentInfo {
    long id;
    String name;
    String suffix;
    String url; // URL
    BloggerInfo owner;
    long filesize;
}
