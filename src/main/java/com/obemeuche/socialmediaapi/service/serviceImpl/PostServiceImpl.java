package com.obemeuche.socialmediaapi.service.serviceImpl;

import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.User;
import com.obemeuche.socialmediaapi.entities.pageCriteria.PostPage;
import com.obemeuche.socialmediaapi.exceptions.DatabaseException;
import com.obemeuche.socialmediaapi.exceptions.PostDoesNotExistException;
import com.obemeuche.socialmediaapi.repositories.PostRepository;
import com.obemeuche.socialmediaapi.repositories.UserRepository;
import com.obemeuche.socialmediaapi.request.PostRequest;
import com.obemeuche.socialmediaapi.response.PostResponse;
import com.obemeuche.socialmediaapi.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<PostResponse> makePost(PostRequest postRequest) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> principalUser = userRepository.findByEmail(user.getUsername());

        Post post = Post
                .builder()
                .content(postRequest.getPost())
                .likes(0)
                .createdDate(LocalDateTime.now())
                .user(principalUser.get())
                .comment(new ArrayList<>())
                .build();

        try {
            postRepository.save(post);
        }catch (Exception e){
            log.info("UNABLE TO SAVE POST TO THE DATABASE. REASON: " + e);
            throw new DatabaseException("UNABLE TO SAVE POST TO THE DATABASE");
        }

        PostResponse response = PostResponse
                .builder()
                .postId(post.getId())
                .content(postRequest.getPost())
                .likes(post.getLikes())
                .comments(post.getComment())
                .createdDate(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<PostResponse> viewPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostDoesNotExistException("POST WITH ID DOES NOT EXISTS!"));

        PostResponse response = PostResponse
                .builder()
                .postId(post.getId())
                .content(post.getContent())
                .likes(post.getLikes())
                .comments(post.getComment())
                .createdDate(post.getCreatedDate())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> likePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostDoesNotExistException("POST WITH ID DOES NOT EXISTS!"));

        post.setLikes(post.getLikes() + 1);

        try {
            postRepository.save(post);
        }catch (Exception e){
            log.info("UNABLE TO SAVE LIKED POST TO THE DATABASE. REASON: " + e);
            throw new DatabaseException("UNABLE TO SAVE LIKED POST TO THE DATABASE");
        }

        return new ResponseEntity<>("POST LIKED", HttpStatus.ACCEPTED);
    }

    @Override
    public Page<Post> fetchPage(PostPage postPage) {
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        Pageable pageable = PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
        Page<Post> post = postRepository.findAll(pageable);
        return post;
    }

    @Override
    public String deletePost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()-> new PostDoesNotExistException("Post not found!"));

        postRepository.delete(post);

        return "Post deleted successfully!";
    }

    @Override
    public ResponseEntity<?> viewPostByDate(String date) {

        //getting current date
        LocalDateTime currentDate = LocalDateTime.now();

        //getting date for 7 days ago
        LocalDateTime sevenDaysAgo = currentDate.minusDays(7);

        List<Post> post;

        if(date == null){
            try {
                post = postRepository.findPostByCreatedDateBetween(sevenDaysAgo, currentDate);
            } catch (Exception e){
                throw new DatabaseException("UNABLE TO CONNECT TO THE DATABASE. REASON: " + e);
            }
        }else {

            // converting the string request date to LocalDateTime
            LocalDateTime creationDate = LocalDateTime.parse(date);

            try {
                post = postRepository.findPostByCreatedDate(creationDate);
            } catch (Exception e) {
                throw new DatabaseException("UNABLE TO CONNECT TO THE DATABASE. REASON: " + e);
            }
        }

        return ResponseEntity.ok().body(post);

        //The product team wants to be able to see the posts by their
        // creation dates, the default filter will be for the last 7 days if there is no date specified
    }
}
