package com.weblog.business.service;

import com.weblog.business.exception.EntityNotFoundException;

public interface SubscribeService {

    void subscribe(long publisher, long fan) throws EntityNotFoundException;

    void unsubscribe(long publisher, long fan) throws EntityNotFoundException;

    boolean subscribed(long pulisher, long fan);
}
