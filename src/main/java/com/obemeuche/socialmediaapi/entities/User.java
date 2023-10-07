package com.obemeuche.socialmediaapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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

    @Column(unique = true)
    @NotNull
    private String username;


    @Email
    @Column(unique = true)
    @NotNull
    private String email;

    private String profilePicture;

    private String password;

    @OneToMany
    private List<Post> posts;

    @ManyToMany
    private List<User> followers;

    @ManyToMany
    private List<User> following;

}
