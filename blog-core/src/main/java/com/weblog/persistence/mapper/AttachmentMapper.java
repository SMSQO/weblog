package com.weblog.persistence.mapper;

import com.weblog.business.entity.AttachmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttachmentMapper {

    void addAttachmentInfo(AttachmentInfo attach, String md5);

    int deleteAttachmentInfo(long aid);

    AttachmentInfo[] getBloggerAttachment(long uid, int from, int count);

    AttachmentInfo getAttachmentInfo(long aid);

    String getAttachmentMd5(long aid);
}
