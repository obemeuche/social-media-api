package com.obemeuche.socialmediaapi.service.serviceImpl;

import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.response.UserResponse;
import com.obemeuche.socialmediaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        if(user.isEmpty()){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User Found");
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), Collections.singleton(authority));
        }
    }

    @Override
    public UserResponse viewProfile(Principal principal) {

        String email = principal.getName();

        Optional<User> principalUser = userRepository.findByEmail(email);

        return UserResponse
                .builder()
                .username(principalUser.get().getUsername())
                .email(principalUser.get().getEmail())
                .profilePicture(principalUser.get().getProfilePicture())
                .posts(principalUser.get().getPosts())
                .followers(principalUser.get().getFollowers())
                .following(principalUser.get().getFollowing())
                .build();
    }
}
