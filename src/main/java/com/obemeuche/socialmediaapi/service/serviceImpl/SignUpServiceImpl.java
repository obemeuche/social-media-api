package com.obemeuche.socialmediaapi.service.serviceImpl;

import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.exceptions.DatabaseException;
import com.obemeuche.socialmediaapi.exceptions.EmailAlreadyExists;
import com.obemeuche.socialmediaapi.exceptions.PasswordNotMatchingException;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.SignUpRequest;
import com.obemeuche.socialmediaapi.response.SignUpResponse;
import com.obemeuche.socialmediaapi.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new PasswordNotMatchingException("PASSWORDS DOES NOT MATCH!");
        }

        boolean emailExists;
        boolean usernameExists;

        try {
            emailExists = userRepository.findByEmail(signUpRequest.getEmail()).isPresent();
            usernameExists = userRepository.findByUsername(signUpRequest.getUsername()).isPresent();
        }catch (Exception e){
            log.info("UNABLE TO CONNECT TO THE DATABASE. REASON: " + e);
            throw new DatabaseException("UNABLE TO CONNECT TO THE DATABASE");
        }

        if (emailExists) {
            throw new EmailAlreadyExists("EMAIL ALREADY EXISTS!");
        }

        if (usernameExists) {
            throw new EmailAlreadyExists("USERNAME ALREADY EXISTS!");
        }

        User user = User
                .builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(signUpRequest.getPassword()))
                .followers(new ArrayList<>())
                .following(new ArrayList<>())
                .posts(new ArrayList<>())
                .build();

        try {
            userRepository.save(user);
        }catch (Exception e){
            log.info("UNABLE TO SAVE USER DETAILS INTO THE DATABASE. REASON: " + e);
            throw new DatabaseException("UNABLE TO SAVE USER DETAILS INTO THE DATABASE");
        }

        SignUpResponse signUpResponse = SignUpResponse.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .message("USER CREATED SUCCESSFULLY!")
                .build();

        return  ResponseEntity.ok().body(signUpResponse);
    }

}
