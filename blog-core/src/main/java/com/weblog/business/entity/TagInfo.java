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

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof TagInfo) && (this.getId() == ((TagInfo) that).getId());
    }
}
