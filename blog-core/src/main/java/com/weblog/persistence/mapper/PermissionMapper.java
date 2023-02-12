package com.weblog.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionMapper {

    boolean checkTagOwner(long uid, long tid);

    boolean checkAttachmentOwner(long uid, long aid);

    boolean checkPostAuthor(long uid, long pid);

    boolean checkPostPublic(long pid);
}
