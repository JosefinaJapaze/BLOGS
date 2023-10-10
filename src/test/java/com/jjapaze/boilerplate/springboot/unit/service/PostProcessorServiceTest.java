package com.jjapaze.boilerplate.springboot.unit.service;

import com.jjapaze.boilerplate.springboot.model.Comment;
import com.jjapaze.boilerplate.springboot.model.Post;
import com.jjapaze.boilerplate.springboot.repository.CommentRepository;
import com.jjapaze.boilerplate.springboot.repository.PostRepository;
import com.jjapaze.boilerplate.springboot.service.BlogService;
import com.jjapaze.boilerplate.springboot.service.PostProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PostProcessorServiceTest {

    @InjectMocks
    private PostProcessorService postProcessorService;

    @Mock
    private BlogService blogService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcessAndPersistData() throws IOException {
        List<Post> newPosts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");

        List<Comment> newComments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setPostId(1L);
        comment1.setBody("Comment 1");

        when(blogService.getCommentsForPostFromEndpoint(anyLong())).thenReturn(newComments);
        when(postRepository.existsById(anyLong())).thenReturn(false);

        postProcessorService.processAndPersistData(newPosts);

        verify(postRepository, times(newPosts.size())).save(any(Post.class));

        verify(blogService, times(newPosts.size())).getCommentsForPostFromEndpoint(anyLong());

        verify(commentRepository, times(newComments.size())).saveAll(anyList());
    }

    @Test
    void testProcessAndPersistData_PostExists() throws IOException {
        List<Post> newPosts = new ArrayList<>();
        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setTitle("Existing Post");
        newPosts.add(existingPost);

        when(postRepository.existsById(existingPost.getId())).thenReturn(true);

        postProcessorService.processAndPersistData(newPosts);

        verify(postRepository, never()).save(any(Post.class));

        verify(blogService, never()).getCommentsForPostFromEndpoint(existingPost.getId());

        verify(commentRepository, never()).saveAll(anyList());
    }

}
