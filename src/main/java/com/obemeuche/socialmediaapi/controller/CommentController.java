package com.obemeuche.socialmediaapi.controller;

import com.obemeuche.socialmediaapi.request.CommentRequest;
import com.obemeuche.socialmediaapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CommentController {

    private final CommentService commentService;

    @PostMapping("api/v1/comment/{id}")
    public ResponseEntity<?> commentOnPost(@PathVariable ("id") Long id, @RequestBody CommentRequest commentRequest){
        return commentService.commentOnPost(id, commentRequest);

    }
}
