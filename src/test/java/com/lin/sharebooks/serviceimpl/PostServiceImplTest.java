package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.model.Post;
import com.lin.sharebooks.service.PostService;
import com.lin.sharebooks.util.DateTimeUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * PostServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/24/2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceImplTest {
    @Autowired
    private PostService postService;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addPost(Post post)
     */
    @Test
    public void testAddPost() throws Exception {
        postService.addPost(new Post("测试帖子标题","测试帖子内容",0, DateTimeUtil.getDate()));
    }

    /**
     * Method: deletePost(int postId)
     */
    @Test
    public void testDeletePost() throws Exception {
        postService.deletePost(2);
    }

    /**
     * Method: findAllPosts()
     */
    @Test
    public void testFindAllPosts() throws Exception {
        System.out.println(postService.findAllPosts());
    }


} 
