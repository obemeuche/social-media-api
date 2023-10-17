package com.obemeuche.socialmediaapi;

import com.obemeuche.socialmediaapi.entities.Comment;
import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.exceptions.CommentNotFoundException;
import com.obemeuche.socialmediaapi.repositories.CommentRepository;
import com.obemeuche.socialmediaapi.repositories.PostRepository;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.CommentRequest;
import com.obemeuche.socialmediaapi.service.serviceImpl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void commentOnPost_SuccessfulComment_ReturnsResponseEntity() {
        Long postId = 1L;
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("Test comment");

        UserDetails userDetails = mock(UserDetails.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        Post mockPost = new Post();
        mockPost.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(mock(Comment.class));

        ResponseEntity<?> responseEntity = commentService.commentOnPost(postId, commentRequest);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteComment_CommentExists_DeletesCommentAndReturnsMessage() {
        Long commentId = 1L;
        Comment mockComment = new Comment();
        mockComment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(mockComment));

        String message = commentService.deleteComment(commentId);

        assertEquals("Comment deleted successfully!", message);
        verify(commentRepository, times(1)).delete(mockComment);
    }

    @Test
    void deleteComment_CommentNotFound_ThrowsCommentNotFoundException() {
        Long commentId = 1L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> commentService.deleteComment(commentId));
    }
}
