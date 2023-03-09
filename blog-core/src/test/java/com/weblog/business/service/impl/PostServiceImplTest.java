package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.PostService;
import com.weblog.persistence.mapper.BloggerMapper;
import com.weblog.persistence.mapper.PostMapper;
import com.weblog.persistence.mapper.SubscribeMapper;
import com.weblog.persistence.mapper.TagMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class PostServiceImplTest{

    private final PostMapper postMapper;
    private final TagMapper tagMapper;
    private final SubscribeMapper subscribeMapper;
    private final BloggerMapper bloggerMapper;
    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String mailSenderAddr;

    private final static String MAIL_TITLE = "您关注的博主发布了新博文";
    private final static String MAIL_CONTENT_PATTERN = "您关注的博主 %s 刚发布了一篇博文, 标题为 %s. 快来看看吧!";
    @Autowired
    private PostService postService;
    @Autowired
    public PostServiceImplTest(
            PostMapper postMapper,
            TagMapper tagMapper,
            SubscribeMapper subscribeMapper,
            BloggerMapper bloggerMapper,
            JavaMailSender sender) {
        this.postMapper = postMapper;
        this.tagMapper = tagMapper;
        this.subscribeMapper = subscribeMapper;
        this.bloggerMapper = bloggerMapper;
        this.sender = sender;
    }

    @Test
    @SneakyThrows
    void listRecommended() {
        int page = 1;
        int pageSize = 10;
        assertNotNull(postService.listRecommended(page,pageSize));
    }

    @SneakyThrows
    void likePost() {
        long bid = 1L;
        long pid = 1L;
        postMapper.updateAddPostLike(pid, 1);
        postMapper.addBloggerLikePost(bid, pid);
    }

    @Test
    @SneakyThrows
    void searchPosts() {
        String[] tagNames = new String[]{"C"};
        String hint = null;
        assertNotEquals(postService.searchPosts(tagNames,hint),new ArrayList<PostInfo>());
    }


    @Test
    @SneakyThrows
    void addPost() {
        long uid = 1L;
        PostInfo post = new PostInfo(7L,"","","",null,null,"","",new PostInfo.PostPermission(),0L,0,0);
        assertEquals(7L, postService.addPost(uid,post));
    }

    @SneakyThrows
    public void updatePostInfo(PostInfo post) {
        val pid = post.getId();
        val ok = postMapper.updatePostInfo(post);
        checkPostExists(ok, pid);
        val tags = post.getTags();
        if (tags != null) {
            // 即使是`tags.isEmpty()`也要更新, 这说明要清除所有标签.
            postMapper.updatePostTags(pid, tags);
        }
    }

    @SneakyThrows
    public void updatePostDetail(long pid, String detail) {
        val ok = postMapper.updatePostDetail(pid, detail);
        checkPostExists(ok, pid);
    }


    @SneakyThrows
    public void deletePost(long pid) throws EntityNotFoundException {
        val ok = postMapper.deletePost(pid);
        log.info(String.format("ok = %s", ok));
        checkPostExists(ok, pid);
    }

    @SneakyThrows
    public PostInfo[] getBloggerAllPosts(long uid, int page, int pageSize) {
        return postMapper.getBloggerAllPostInfo(uid, page * pageSize, pageSize);
    }

    @SneakyThrows
    public PostInfo[] getBloggerPublicPosts(long uid, int page, int pageSize) {
        return postMapper.getBloggerPublicPostInfo(uid, page, pageSize);
    }

    @SneakyThrows
    public PostInfo getPostInfo(long pid) throws EntityNotFoundException {
        val post = postMapper.getPostInfo(pid);
        checkPostExists(post, pid);
        return post;
    }

    @SneakyThrows
    public String getPostDetail(long pid) throws EntityNotFoundException {
        val detail = postMapper.getPostDetail(pid);
        checkPostExists(detail, pid);

        return detail;
    }

    @SneakyThrows
    public void updateAddPostLikes(long pid, int likes) throws EntityNotFoundException {
        val ok = postMapper.updateAddPostLike(pid, likes);
        checkPostExists(ok, pid);
    }

    @SneakyThrows
    public void updateAddPostView(long pid, int views) throws EntityNotFoundException {
        val ok = postMapper.updateAddPostView(pid, views);
        checkPostExists(ok, pid);
    }

    private void checkPostExists(final int ok, final long pid) throws EntityNotFoundException {
        if (ok != 0) return;
        throw new EntityNotFoundException(String.format("Post not found with pid = %d", pid));
    }

    private void checkPostExists(Object obj, final long pid) throws EntityNotFoundException {
        if (obj != null) return;
        throw new EntityNotFoundException(String.format("Post not found with pid = %d", pid));
    }

    private void notifyFan(BloggerInfo fan, PostInfo post) {
        try {
            val mailReceiverAddr = fan.getEmail();

            val message = new SimpleMailMessage();
            message.setFrom(mailSenderAddr);
            message.setTo(mailReceiverAddr);
            message.setSubject(MAIL_TITLE);
            message.setText(String.format(MAIL_CONTENT_PATTERN, post.getBlogger().getName(), post.getTitle()));

            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // TODO NOT A GOOD PRACTICE.
            // 不要吞掉异常, 如果有问题就应该让它直接暴露出来.
        }
    }
}
