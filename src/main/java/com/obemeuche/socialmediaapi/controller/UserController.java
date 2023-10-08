package com.obemeuche.socialmediaapi.controller;

import com.obemeuche.socialmediaapi.request.SignInRequest;
import com.obemeuche.socialmediaapi.request.SignUpRequest;
import com.obemeuche.socialmediaapi.response.UserResponse;
import com.obemeuche.socialmediaapi.service.SignInService;
import com.obemeuche.socialmediaapi.service.SignUpService;
import com.obemeuche.socialmediaapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class UserController {

    private final SignUpService signUpService;

    private final SignInService signInService;

    private final UserService userService;

    @PostMapping("/api/v1/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }

    @PostMapping("/api/v1/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        return signInService.signIn(signInRequest);
    }

    @GetMapping("/api/v1/view-profile")
    private UserResponse viewProfile(Principal principal){
        return userService.viewProfile(principal);
    }
}
