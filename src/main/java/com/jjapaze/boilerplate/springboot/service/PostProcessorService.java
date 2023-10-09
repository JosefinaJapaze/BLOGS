package com.jjapaze.boilerplate.springboot.service;

import com.jjapaze.boilerplate.springboot.model.Comment;
import com.jjapaze.boilerplate.springboot.model.Post;
import com.jjapaze.boilerplate.springboot.repository.CommentRepository;
import com.jjapaze.boilerplate.springboot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PostProcessorService {
    private final BlogService blogService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostProcessorService(BlogService blogService, PostRepository postRepository, CommentRepository commentRepository) {
        this.blogService = blogService;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void processAndPersistData(List<Post> newPosts) throws IOException {
        for (Post post : newPosts) {
            if (!postRepository.existsById(post.getId())) {
                postRepository.save(post);

                List<Comment> newComments = blogService.getCommentsForPostFromEndpoint(post.getId());

                commentRepository.saveAll(newComments);
            }
        }
    }

}