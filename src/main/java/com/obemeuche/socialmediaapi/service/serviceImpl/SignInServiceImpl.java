package com.obemeuche.socialmediaapi.service.serviceImpl;

import com.obemeuche.socialmediaapi.config.JwtUtils;
import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.SignInRequest;
import com.obemeuche.socialmediaapi.response.SignInResponse;
import com.obemeuche.socialmediaapi.service.SignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignInServiceImpl implements SignInService{

    private final UserRepository userRepository;

    private final UserServiceImpl userService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {

        //checks if user exists
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND!")));

        try{
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken
                    (signInRequest.getEmail(), signInRequest.getPassword())
            );
        } catch (BadCredentialsException ex){
            throw new UsernameNotFoundException("Invalid Credential");
        }
        final UserDetails userDetails = userService.loadUserByUsername(signInRequest.getEmail());
        String jwt = jwtUtils.generateToken(userDetails);

        SignInResponse signInResponse = SignInResponse
                .builder()
                .email(signInRequest.getEmail())
                .message("LOGIN SUCCESSFUL!")
                .token(jwt)
                .build();

        return ResponseEntity.ok().body(signInResponse);
    }
}
