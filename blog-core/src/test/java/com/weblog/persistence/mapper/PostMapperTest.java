package com.weblog.persistence.mapper;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.entity.TagInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class PostMapperTest {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Autowired
    private PostMapper postMapper;
    @Test
    void listRecommendPostInfo() {
        val blogger = bloggerMapper.getBloggerById(2);

        TagInfo [] tagInfos = new TagInfo[]{new TagInfo(6, "Haskell", null, "desc-haskell")};

        val permission  = new PostInfo.PostPermission(true,false);

      assertArrayEquals(new PostInfo[]{new PostInfo(6,"Haskell标题","Haskell简介","/blog/2/post/6/detail",blogger, List.of(tagInfos),"","/blog/2/post/6/comment",permission,1,1,0)},
              postMapper.listRecommendPostInfo(0,1));


    }

    @Test
    void getBloggerLikedPosts() {
        val blogger = bloggerMapper.getBloggerById(1);

        TagInfo [] tagInfos = new TagInfo[]{
                new TagInfo(1, "java", null, "desc-java"),
        };

        val permission  = new PostInfo.PostPermission(false,false);



        assertArrayEquals(new PostInfo[]{new PostInfo(1,"Java标题","Java简介","/blog/1/post/1/detail",blogger, List.of(tagInfos),"","/blog/1/post/1/comment",permission,1,1,1)},
                postMapper.getBloggerLikedPosts(1));
    }

    @Test
    void searchPostsByTagAndTitleHint() {
        val blogger = bloggerMapper.getBloggerById(1);
        List<PostInfo> posts =  new ArrayList<>();

        TagInfo [] tagInfos = new TagInfo[]{
                new TagInfo(2, "C", null, "desc-C"),
                new TagInfo(3, "C++", null, "desc-Cpp"),

        };

        val permission  = new PostInfo.PostPermission(true,false);


        posts.add(new PostInfo(4,"C++标题","C++简介","/blog/1/post/4/detail",blogger, List.of(tagInfos),"","/blog/1/post/4/comment",permission,1,1,2));
        assertArrayEquals(posts.toArray(),
                postMapper.searchPostsByTagAndTitleHint(3L,"C++").toArray());
    }

    @Test
    void searchPostsByTagAndDetailHint() {
        val blogger = bloggerMapper.getBloggerById(1);
        List<PostInfo> posts =  new ArrayList<>();


        TagInfo [] tagInfos = new TagInfo[]{
                new TagInfo(2, "C", null, "desc-C"),
                new TagInfo(3, "C++", null, "desc-Cpp"),

        };

        val permission  = new PostInfo.PostPermission(true,false);

        posts.add(new PostInfo(4,"C++标题","C++简介","/blog/1/post/4/detail",blogger, List.of(tagInfos),"","/blog/1/post/4/comment",permission,1,1,2));
        assertArrayEquals(posts.toArray(),
                postMapper.searchPostsByTagAndTitleHint(3l,"C++").toArray());
    }

    @Test
    void addBloggerLikePost() {
        postMapper.addBloggerLikePost(4,1);
        assertNotEquals(0,List.of(postMapper.getBloggerLikedPosts(4)).contains(1));
    }

    @Test
    void getBloggerAllPostInfo() {
        val blogger1 = bloggerMapper.getBloggerById(1);

        TagInfo [] tagInfos1 = new TagInfo[]{
                new TagInfo(1, "java", null, "desc-java"),
        };

        val permission1  = new PostInfo.PostPermission(false,false);


        assertArrayEquals(new PostInfo[]{
                new PostInfo(1,"Java标题","Java简介","/blog/1/post/1/detail",blogger1, List.of(tagInfos1),"","/blog/1/post/1/comment",permission1,1,1,1)        },postMapper.getBloggerAllPostInfo(1,0,1));
    }

    @Test
    void getBloggerPublicPostInfo() {
        val blogger = bloggerMapper.getBloggerById(2);

        TagInfo [] tagInfos = new TagInfo[]{new TagInfo(6, "Haskell", null, "desc-haskell")};

        val permission  = new PostInfo.PostPermission(true,false);

        assertArrayEquals(new PostInfo[]{new PostInfo(6,"Haskell标题","Haskell简介","/blog/2/post/6/detail",blogger, List.of(tagInfos),"","/blog/2/post/6/comment",permission,1,1,0)},
                postMapper.getBloggerPublicPostInfo(2,0,1));

    }




    @Test
    void getPostInfo() {
        val blogger = bloggerMapper.getBloggerById(2);

        TagInfo [] tagInfos = new TagInfo[]{new TagInfo(6, "Haskell", null, "desc-haskell")};

        val permission  = new PostInfo.PostPermission(true,false);
        assertEquals(new PostInfo(6,"Haskell标题","Haskell简介","/blog/2/post/6/detail",blogger, List.of(tagInfos),"","/blog/2/post/6/comment",permission,1,1,0),postMapper.getPostInfo(6) );
    }

    @Test
    void getPostDetail() {
        val blogger = bloggerMapper.getBloggerById(2);

        TagInfo [] tagInfos = new TagInfo[]{new TagInfo(6, "Haskell", null, "desc-haskell")};

        val permission  = new PostInfo.PostPermission(true,false);
        assertEquals("Haskell详情",postMapper.getPostDetail(6) );

    }

    @Test
    void addPost() {
        val blogger = bloggerMapper.getBloggerById(2);

        TagInfo [] tagInfos = new TagInfo[]{new TagInfo(6, "Haskell", null, "desc-haskell")};
        val permission  = new PostInfo.PostPermission(true,false);

        assertEquals(1,postMapper.addPost(new PostInfo(7,"深度学习标题","深度学习简介","/blog/2/post/7/detail",blogger, List.of(tagInfos),"","/blog/2/post/6/comment",permission,1,1,0)));

    }

    @Test
    void updatePostInfo() {

        val blogger = bloggerMapper.getBloggerById(2);

        TagInfo [] tagInfos = new TagInfo[]{new TagInfo(6, "Haskell", null, "desc-haskell")};
        val permission  = new PostInfo.PostPermission(true,false);
        assertEquals(1,postMapper.updatePostInfo(new PostInfo(6,"Haskell标题","Haskell内容","/blog/2/post/6/detail",blogger, List.of(tagInfos),"","/blog/2/post/6/comment",permission,1,1,0)));

    }

    @Test
    void updatePostTags() {
        TagInfo [] tagInfos1 = new TagInfo[]{
                new TagInfo(1, "java", null, "desc-java"),
        };

        assertEquals(1,postMapper.updatePostTags(6,List.of(tagInfos1)));
    }

    @Test
    void updatePostDetail() {
        assertEquals(1,postMapper.updatePostDetail(6,"hhhhhhh"));

    }

    @Test
    void deletePost() {
        assertEquals(1,postMapper.deletePost(6));


    }

    @Test
    void updateAddPostLike() {
        assertEquals(1,postMapper.updateAddPostLike(6,5));
    }

    @Test
    void updateAddPostView() {
        assertEquals(1,postMapper.updateAddPostLike(6,10));

    }

    @Test
    void findTagIdByNameAndAuthor() {
        assertEquals(9,postMapper.findTagIdByNameAndAuthor("docker",2));

    }
}