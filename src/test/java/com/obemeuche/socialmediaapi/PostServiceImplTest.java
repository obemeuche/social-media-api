package com.obemeuche.socialmediaapi;

import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.repositories.PostRepository;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.PostRequest;
import com.obemeuche.socialmediaapi.response.PostResponse;
import com.obemeuche.socialmediaapi.service.serviceImpl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void makePost_SuccessfulPostCreation_ReturnsResponseEntity() {
        PostRequest postRequest = new PostRequest();
        postRequest.setPost("Test post content");

        UserDetails userDetails = mock(UserDetails.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        when(postRepository.save(any(Post.class))).thenReturn(mock(Post.class));

        ResponseEntity<PostResponse> responseEntity = postService.makePost(postRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void viewPost_PostExists_ReturnsResponseEntity() {
        Long postId = 1L;
        Post mockPost = new Post();
        mockPost.setId(postId);
        mockPost.setContent("Test post content");

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        ResponseEntity<PostResponse> responseEntity = postService.viewPost(postId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deletePost_PostExists_DeletesPostAndReturnsMessage() {
        Long postId = 1L;
        Post mockPost = new Post();
        mockPost.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        String message = postService.deletePost(postId);

        assertEquals("Post deleted successfully!", message);
        verify(postRepository, times(1)).delete(mockPost);
    }

}
