package com.weblog.controller;

import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.service.BloggerService;
import com.weblog.business.service.SubscribeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogger/{uid}")
public class SubscribeController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public void subscribe(
            @PathVariable("uid") long publisher
    ) throws EntityNotFoundException, NotLoggedInException {
        val fan = bloggerService.getSelfBloggerId(); // exception will be thrown if not found.
        subscribeService.subscribe(publisher, fan);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(
            @PathVariable("uid") long publisher
    ) throws EntityNotFoundException, NotLoggedInException {
        val fan = bloggerService.getSelfBloggerId();
        subscribeService.unsubscribe(publisher, fan);
    }

    @GetMapping("/subscribed")
    public boolean subscribed(
            @PathVariable("uid") long publisher
    ) throws NotLoggedInException {
        val fan = bloggerService.getSelfBloggerId();
        return subscribeService.subscribed(publisher, fan);
    }

}
