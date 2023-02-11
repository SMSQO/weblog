package com.weblog.business.service.impl;

import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.SubscribeService;
import com.weblog.persistence.mapper.SubscribeMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private SubscribeMapper subscribeMapper;


    @Override
    public void subscribe(long publisher, long fan) throws EntityNotFoundException {
        if (!subscribeMapper.bothBloggerExists(publisher, fan)) {
            throw new EntityNotFoundException(String.format("Either publisher or fan not found. ids = [%d, %d]", publisher, fan));
        }
        subscribeMapper.subscribe(publisher, fan);
    }

    @Override
    public void unsubscribe(long publisher, long fan) throws EntityNotFoundException {
        val ok = subscribeMapper.unsubscribe(publisher, fan);
        if (ok != 1) {
            throw new EntityNotFoundException(String.format("Either publisher or fan not found. ids = [%d, %d]", publisher, fan));
        }
    }

    @Override
    public boolean subscribed(long publisher, long fan) {
        return subscribeMapper.subscribed(publisher, fan);
    }
}
