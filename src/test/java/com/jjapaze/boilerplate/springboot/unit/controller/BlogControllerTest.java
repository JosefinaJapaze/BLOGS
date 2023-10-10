package com.jjapaze.boilerplate.springboot.unit.controller;

import com.jjapaze.boilerplate.springboot.controller.BlogController;
import com.jjapaze.boilerplate.springboot.model.Post;
import com.jjapaze.boilerplate.springboot.service.BlogService;
import com.jjapaze.boilerplate.springboot.service.PostProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BlogControllerTest {

    @InjectMocks
    private BlogController blogController;

    @Mock
    private BlogService blogService;

    @Mock
    private PostProcessorService postProcessorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUpdatePosts() throws IOException {

        List<Post> posts = new ArrayList<>();
        Post post1 = new Post();
        post1.setTitle("Post 1");
        posts.add(post1);

        when(blogService.getPostsFromEndpoint()).thenReturn(posts);

        ResponseEntity<List<Post>> responseEntity = blogController.updatePosts();

        verify(blogService, times(1)).getPostsFromEndpoint();
        verify(postProcessorService, times(1)).processAndPersistData(posts);

        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == posts);
    }

    @Test
    void testUpdatePosts_Exception() throws IOException {
        when(blogService.getPostsFromEndpoint()).thenThrow(new IOException());

        assertThrows(IOException.class, () -> blogController.updatePosts());
        verify(blogService, times(1)).getPostsFromEndpoint();
    }
}

