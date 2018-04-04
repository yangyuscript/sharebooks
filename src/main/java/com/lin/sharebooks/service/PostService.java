package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Post;

import java.util.List;

public interface PostService {
    void addPost(Post post) throws Exception;
    void deletePost(int postId) throws Exception;
    List<Post> findAllPosts() throws Exception;
    List<Post> findAllWithTerms(String title,String time) throws Exception;
    Post getByPostid(int postid) throws Exception;
}
