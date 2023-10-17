package com.obemeuche.socialmediaapi.controller;

import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.pageCriteria.PostPage;
import com.obemeuche.socialmediaapi.request.PostRequest;
import com.obemeuche.socialmediaapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/create-post")
    public ResponseEntity<?> makePost(@RequestBody PostRequest postRequest) {
        return postService.makePost(postRequest);
    }

    @GetMapping("/api/v1/view-post/{id}")
    public ResponseEntity<?> viewPost(@PathVariable("id") Long id) {
        return postService.viewPost(id);
    }

    @GetMapping("/api/v1/view-all-post")
    public ResponseEntity<Page<Post>> fetchPage(PostPage postPage) {
        return ResponseEntity.ok(postService.fetchPage(postPage));
    }

    @GetMapping("/api/v1/view-post-by-date")
    public ResponseEntity<?> viewPostByDate(@RequestParam(required = false, name = "date") String date) {
        return postService.viewPostByDate(date);
    }

    @DeleteMapping("/api/v1/delete-post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
