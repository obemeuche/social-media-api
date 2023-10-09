package com.obemeuche.socialmediaapi;

import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.response.UserResponse;
import com.obemeuche.socialmediaapi.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    void viewProfile() {
        String email = "test@example.com";
        Principal principal = () -> email;
        User user = new User();
        user.setEmail(email);
        user.setUsername("TestUser");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.viewProfile(principal);

        assertNotNull(userResponse);
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getEmail(), userResponse.getEmail());
    }

    @Test
    void followUser_UserExists_FollowsUserAndReturnsResponseEntity() {
        Long followerId = 1L;
        Long followingId = 2L;
        User follower = new User();
        User following = new User();
        follower.setId(followerId);
        following.setId(followingId);
        follower.setFollowing(new HashSet<>());
        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userRepository.findById(followingId)).thenReturn(Optional.of(following));

        ResponseEntity<?> responseEntity = userService.followUser(followerId, followingId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(follower.getFollowing().contains(following));
    }

    @Test
    void unfollowUser_UserExists_UnfollowsUserAndReturnsResponseEntity() {
        Long followerId = 1L;
        Long followingId = 2L;
        User follower = new User();
        User following = new User();
        follower.setId(followerId);
        following.setId(followingId);
        follower.setFollowing((Set<User>) Collections.singletonList(following));
        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userRepository.findById(followingId)).thenReturn(Optional.of(following));

        ResponseEntity<?> responseEntity = userService.unfollowUser(followerId, followingId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(follower.getFollowing().contains(following));
    }
}
