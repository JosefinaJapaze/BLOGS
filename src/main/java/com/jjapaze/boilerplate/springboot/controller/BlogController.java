package com.jjapaze.boilerplate.springboot.controller;

import com.jjapaze.boilerplate.springboot.model.Post;
import com.jjapaze.boilerplate.springboot.service.BlogService;
import com.jjapaze.boilerplate.springboot.service.PostProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final PostProcessorService postProcessorService;

    @GetMapping("/update")
    public ResponseEntity<List<Post>> updatePosts() throws IOException {

        List<Post> posts = blogService.getPostsFromEndpoint();
        postProcessorService.processAndPersistData(posts);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}