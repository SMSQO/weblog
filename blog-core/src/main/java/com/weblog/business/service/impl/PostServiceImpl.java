package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.PostService;
import com.weblog.persistence.mapper.PostMapper;
import com.weblog.persistence.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final static String POST_COMMENT_URL = "/blog/%d/post/%d/comment";
    private final static String POST_DETAIL_URL = "/blog/%d/post/%d/detail";

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TagMapper tagMapper;

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
                    .map(it -> tagMapper.getTagInfoByName(it))
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
        val blogger = new BloggerInfo();
        blogger.setId(uid);
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
        val postList = postMapper.getBloggerAllPostInfo(uid, page * pageSize, pageSize);
        for (val it : postList) {
            padPostInfo(it);
        }
        return postList;
    }

    @Override
    public PostInfo[] getBloggerPublicPosts(long uid, int page, int pageSize) {
        val postList = postMapper.getBloggerPublicPostInfo(uid, page, pageSize);
        for (val it : postList) {
            padPostInfo(it);
        }
        return postList;
    }

    @Override
    public PostInfo getPostInfo(long pid) throws EntityNotFoundException {
        val post = postMapper.getPostInfo(pid);
        checkPostExists(post, pid);
        return padPostInfo(post);
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

    private PostInfo padPostInfo(PostInfo post) {
        val uid = post.getBlogger().getId();
        val pid = post.getId();
        String commentsUrl = String.format(POST_COMMENT_URL, uid, pid);
        String detailUrl = String.format(POST_DETAIL_URL, uid, pid);
        post.setComments(commentsUrl);
        post.setDetail(detailUrl);
        return post;
    }
}
