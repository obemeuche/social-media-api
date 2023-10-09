package com.obemeuche.socialmediaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "USER_EMAIL", unique = true)
    private String email;

    @Column(name = "USER_PROFILE_PICTURE")
    private String profilePicture;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Column(name = "POST")
    private List<Post> posts;

    @ManyToMany
    @Column(name = "FOLLOWERS")
    private List<User> followers;

    @ManyToMany
    @Column(name = "FOLLOWING")
    private List<User> following;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts.size() +
                ", followers=" + followers.size() +
                ", following=" + following.size() +
                '}';
    }
}
