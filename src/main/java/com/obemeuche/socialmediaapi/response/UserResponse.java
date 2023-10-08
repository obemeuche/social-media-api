package com.obemeuche.socialmediaapi.response;

import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class UserResponse {
    private String username;

    private String email;

    private String profilePicture;

    private List<Post> posts;

    private List<User> followers;

    private List<User> following;
}
