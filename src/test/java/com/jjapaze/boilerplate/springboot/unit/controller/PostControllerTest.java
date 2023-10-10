package com.jjapaze.boilerplate.springboot.unit.controller;

import com.jjapaze.boilerplate.springboot.controller.PostController;
import com.jjapaze.boilerplate.springboot.model.Comment;
import com.jjapaze.boilerplate.springboot.model.Post;
import com.jjapaze.boilerplate.springboot.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetPostById_OK() {
        Long postId = 1L;
        Post mockedPost = new Post();
        mockedPost.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockedPost));

        ResponseEntity<Post> responseEntity = postController.getPostById(postId);

        verify(postRepository, times(1)).findById(postId);

        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == mockedPost);
    }

    @Test
    void testGetPostById_NotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        ResponseEntity<Post> responseEntity = postController.getPostById(postId);

        verify(postRepository, times(1)).findById(postId);

        assert(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
        assert(responseEntity.getBody() == null);
    }

    @Test
    void testSearchPostsByTitle_Success() {
        String searchTerm = "example";
        List<Post> mockedPosts = new ArrayList<>();
        Post post1 = new Post();
        post1.setTitle("Example Post 1");
        mockedPosts.add(post1);

        when(postRepository.findByTitleContaining(searchTerm)).thenReturn(mockedPosts);

        ResponseEntity<List<Post>> responseEntity = postController.searchPostsByTitle(searchTerm);

        verify(postRepository, times(1)).findByTitleContaining(searchTerm);

        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == mockedPosts);
    }

    @Test
    void testSearchPostsByTitle_EmptyResult() {
        String searchTerm = "nonexistent";
        List<Post> emptyList = new ArrayList<>();

        when(postRepository.findByTitleContaining(searchTerm)).thenReturn(emptyList);

        ResponseEntity<List<Post>> responseEntity = postController.searchPostsByTitle(searchTerm);

        verify(postRepository, times(1)).findByTitleContaining(searchTerm);

        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody().isEmpty());
    }

    @Test
    void testSearchPostsByTitle_NullSearchTerm() {
        String searchTerm = null;

        ResponseEntity<List<Post>> responseEntity = postController.searchPostsByTitle(searchTerm);

        verify(postRepository, never()).findByTitleContaining(anyString());

        assert(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(responseEntity.getBody() == null);
    }

    @Test
    void testGetCommentsForPost_OK() {
        Long postId = 1L;
        List<Comment> mockedComments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setBody("Comment 1");
        mockedComments.add(comment1);

        Post mockedPost = new Post();
        mockedPost.setId(postId);
        mockedPost.setComments(mockedComments);

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockedPost));

        ResponseEntity<List<Comment>> responseEntity = postController.getCommentsForPost(postId);

        verify(postRepository, times(1)).findById(postId);

        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == mockedComments);
    }

    @Test
    void testGetCommentsForPost_NotFound() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        ResponseEntity<List<Comment>> responseEntity = postController.getCommentsForPost(postId);

        verify(postRepository, times(1)).findById(postId);

        assert(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
        assert(responseEntity.getBody() == null);
    }

    @Test
    void testGetCommentsForPost_EmptyComments() {
        Long postId = 1L;
        Post mockedPost = new Post();
        mockedPost.setId(postId);
        mockedPost.setComments(new ArrayList<>());

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockedPost));

        ResponseEntity<List<Comment>> responseEntity = postController.getCommentsForPost(postId);

        verify(postRepository, times(1)).findById(postId);

        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody().isEmpty());
    }

    @Test
    void testGetPaginatedPosts_OK() {
        int page = 0;
        int size = 10;
        List<Post> mockedPosts = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> mockedPage = new PageImpl<>(mockedPosts, pageRequest, mockedPosts.size());

        when(postRepository.findAll(pageRequest)).thenReturn(mockedPage);

        Page<Post> result = postController.getPaginatedPosts(page, size);

        verify(postRepository, times(1)).findAll(pageRequest);

        assert(result.equals(mockedPage));
    }

    @Test
    void testGetPaginatedPosts_DefaultValues() {
        int defaultPage = 0;
        int defaultSize = 10;
        List<Post> mockedPosts = new ArrayList<>();

        PageRequest defaultPageRequest = PageRequest.of(defaultPage, defaultSize);
        Page<Post> mockedPage = new PageImpl<>(mockedPosts, defaultPageRequest, mockedPosts.size());

        when(postRepository.findAll(defaultPageRequest)).thenReturn(mockedPage);

        Page<Post> result = postController.getPaginatedPosts(defaultPage, defaultSize);

        verify(postRepository, times(1)).findAll(defaultPageRequest);

        assert(result.equals(mockedPage));
    }

    @Test
    void testGetPaginatedPosts_EmptyResult() {
        int page = 0;
        int size = 10;
        List<Post> emptyList = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> mockedPage = new PageImpl<>(emptyList, pageRequest, emptyList.size());

        when(postRepository.findAll(pageRequest)).thenReturn(mockedPage);

        Page<Post> result = postController.getPaginatedPosts(page, size);

        verify(postRepository, times(1)).findAll(pageRequest);

        assert(result.equals(mockedPage));
    }
}
