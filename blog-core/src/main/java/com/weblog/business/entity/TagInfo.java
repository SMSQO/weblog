package com.weblog.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagInfo {
    long id;
    String name;
    BloggerInfo owner;
    String description;
}
