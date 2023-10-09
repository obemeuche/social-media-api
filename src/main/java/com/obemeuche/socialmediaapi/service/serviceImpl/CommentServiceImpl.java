package com.obemeuche.socialmediaapi.service.serviceImpl;

import com.obemeuche.socialmediaapi.entities.Comment;
import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.exceptions.DatabaseException;
import com.obemeuche.socialmediaapi.exceptions.PostDoesNotExistException;
import com.obemeuche.socialmediaapi.repositories.CommentRepository;
import com.obemeuche.socialmediaapi.repositories.PostRepository;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.CommentRequest;
import com.obemeuche.socialmediaapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    public ResponseEntity<?> commentOnPost(Long id, CommentRequest commentRequest) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> principalUser = userRepository.findByEmail(user.getUsername());

        Post post = postRepository.findById(id).orElseThrow(() -> new PostDoesNotExistException("POST WITH THIS ID DOES NOT EXISTS!"));

        Comment comment = Comment
                .builder()
                .comment(commentRequest.getComment())
                .post(post)
                .user(principalUser.get())
                .build();

        post.getComment().add(comment);

        try {
            commentRepository.save(comment);
            postRepository.save(post);

        }catch (Exception e){
            log.info("UNABLE TO SAVE COMMENT ON THE DATABASE. REASON: " + e);
            throw new DatabaseException("UNABLE TO SAVE COMMENT ON THE DATABASE.");
        }

        return ResponseEntity.ok().body(comment);
    }
}
