package com.obemeuche.socialmediaapi;

import com.obemeuche.socialmediaapi.config.JwtUtils;
import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.SignInRequest;
import com.obemeuche.socialmediaapi.response.SignInResponse;
import com.obemeuche.socialmediaapi.service.serviceImpl.SignInServiceImpl;
import com.obemeuche.socialmediaapi.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignInServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private SignInServiceImpl signInService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void signIn_SuccessfulSignIn_ReturnsResponseEntity() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("test@example.com");
        signInRequest.setPassword("password");

        User mockUser = new User();
        mockUser.setEmail(signInRequest.getEmail());

        UserDetails userDetails = mock(UserDetails.class);
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(userService.loadUserByUsername(signInRequest.getEmail())).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn("mockToken");

        ResponseEntity<SignInResponse> responseEntity = signInService.signIn(signInRequest);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("LOGIN SUCCESSFUL!", responseEntity.getBody().getMessage());
        assertNotNull(responseEntity.getBody().getToken());
    }
}
