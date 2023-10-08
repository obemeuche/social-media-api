package com.obemeuche.socialmediaapi.response;

import com.obemeuche.socialmediaapi.entities.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostResponse {

    private String content;
    private LocalDateTime createdDate;
    private int likes;
    private List<Comment> comments;
}
