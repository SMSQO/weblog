package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagInfo {
    long id;
    String name;
    BloggerInfo owner;
    String description;
}
