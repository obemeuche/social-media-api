package com.obemeuche.socialmediaapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "USER_EMAIL", unique = true)
    private String email;

    @Column(name = "USER_PROFILE_PICTURE")
    private String profilePicture;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany
    @Column(name = "POST")
    private List<Post> posts;

    @ManyToMany
    @Column(name = "FOLLOWERS")
    private List<User> followers;

    @ManyToMany
    @Column(name = "FOLLOWING")
    private List<User> following;

}
