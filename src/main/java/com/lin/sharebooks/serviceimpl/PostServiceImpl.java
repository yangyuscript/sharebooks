package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.PostMapper;
import com.lin.sharebooks.model.Post;
import com.lin.sharebooks.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired(required = false)
    private PostMapper postMapper;

    @Override
    public void addPost(Post post) throws Exception {
        postMapper.insert(post);
    }

    @Override
    public void deletePost(int postId) throws Exception {
        postMapper.deleteByPrimaryKey(postId);
    }

    @Override
    public List<Post> findAllPosts() throws Exception {
        return postMapper.selectAll();
    }


    @Override
    public List<Post> findAllWithTerms(String title,String time) throws Exception {
        return postMapper.findAllWithTerms(title,time);
    }
}
