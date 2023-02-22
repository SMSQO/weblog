package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.PostService;
import com.weblog.persistence.mapper.BloggerMapper;
import com.weblog.persistence.mapper.PostMapper;
import com.weblog.persistence.mapper.SubscribeMapper;
import com.weblog.persistence.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

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
    public PostServiceImpl(
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

    @Override
    public PostInfo[] listRecommended(int page, int pageSize) {
        return postMapper.listRecommendPostInfo(page * pageSize, pageSize);
    }

    @Override
    public void likePost(long bid, long pid) {
        postMapper.updateAddPostLike(pid, 1);
        postMapper.addBloggerLikePost(bid, pid);
    }

    @Override
    public List<PostInfo> searchPosts(String[] tagNames, @Nullable String hint) {
        if (tagNames.length != 0) {
            return Arrays.stream(tagNames)
                    .map(tagMapper::getTagInfoByName)
                    .filter(Objects::nonNull)
                    .flatMap(tag -> {
                        val tid = tag.getId();
                        val titleStream = postMapper.searchPostsByTagAndTitleHint(tid, hint).stream();
                        val detailStream = postMapper.searchPostsByTagAndDetailHint(tid, hint).stream();
                        return Stream.concat(titleStream, detailStream);
                    }).distinct()
                    .collect(Collectors.toList());
        } else {
            return Stream.concat(
                    postMapper.searchPostsByTagAndTitleHint(null, hint).stream(),
                    postMapper.searchPostsByTagAndDetailHint(null, hint).stream()
            ).distinct().collect(Collectors.toList());
        }
    }


    @Override
    public long addPost(long uid, PostInfo post) {
        val blogger = bloggerMapper.getBloggerById(uid); // 后面要通知粉丝, 所以得查询出博主的名字
        post.setBlogger(blogger);
        postMapper.addPost(post);
        val pid = post.getId();
        val tags = post.getTags();
        // if so, normalize tags
        if (tags != null) {
            // tags.forEach { it -> /* set owner and id of it */ }
            for (val tag : tags) {
                // Tag's not exist <=> tag.id == null.
                // Before updating tags of post, we should first make sure
                // all these tags exists, i.e. set id of tags.
                var tid = postMapper.findTagIdByNameAndAuthor(tag.getName(), uid);
                if (tid != null) {
                    tag.setId(tid);
                    continue;
                }
                // Tag doesn't exist. Add tag to database.
                tag.setOwner(new BloggerInfo(uid));
                tag.setDescription("");
                tagMapper.addTag(tag);
            }
            postMapper.updatePostTags(pid, tags);
        }
        if (post.getPermission().isPublic()) {
            for (val fan : subscribeMapper.getOnesFans(uid)) {
                notifyFan(fan, post);
            }
        }
        return pid;
    }

    @Override
    public void updatePostInfo(PostInfo post) throws EntityNotFoundException {
        val pid = post.getId();
        val ok = postMapper.updatePostInfo(post);
        checkPostExists(ok, pid);
        val tags = post.getTags();
        if (tags != null) {
            // 即使是`tags.isEmpty()`也要更新, 这说明要清除所有标签.
            postMapper.updatePostTags(pid, tags);
        }
    }

    @Override
    public void updatePostDetail(long pid, String detail) throws EntityNotFoundException {
        val ok = postMapper.updatePostDetail(pid, detail);
        checkPostExists(ok, pid);
    }

    @Override
    public void deletePost(long pid) throws EntityNotFoundException {
        val ok = postMapper.deletePost(pid);
        log.info(String.format("ok = %s", ok));
        checkPostExists(ok, pid);
    }

    @Override
    public PostInfo[] getBloggerAllPosts(long uid, int page, int pageSize) {
        return postMapper.getBloggerAllPostInfo(uid, page * pageSize, pageSize);
    }

    @Override
    public PostInfo[] getBloggerPublicPosts(long uid, int page, int pageSize) {
        return postMapper.getBloggerPublicPostInfo(uid, page, pageSize);
    }

    @Override
    public PostInfo getPostInfo(long pid) throws EntityNotFoundException {
        val post = postMapper.getPostInfo(pid);
        checkPostExists(post, pid);
        return post;
    }

    @Override
    public String getPostDetail(long pid) throws EntityNotFoundException {
        val detail = postMapper.getPostDetail(pid);
        checkPostExists(detail, pid);

        return detail;
    }

    @Override
    public void updateAddPostLikes(long pid, int likes) throws EntityNotFoundException {
        val ok = postMapper.updateAddPostLike(pid, likes);
        checkPostExists(ok, pid);
    }

    @Override
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
