package com.obemeuche.socialmediaapi.controller;

import com.obemeuche.socialmediaapi.request.CreatePostRequest;
import com.obemeuche.socialmediaapi.response.PostResponse;
import com.obemeuche.socialmediaapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/create-post")
    public PostResponse makePost(@RequestBody CreatePostRequest postRequest){
        return postService.makePost(postRequest);
    }


    @GetMapping("api/v1/view-post/{id}")
    public PostResponse viewPost(@PathVariable("id") Long id){
        return postService.viewPost(id);
    }

    @PostMapping("api/v1/like-post/{id}")
    public ResponseEntity<?> likePost(@PathVariable("id") Long id){
        return postService.likePost(id);
    }
}
