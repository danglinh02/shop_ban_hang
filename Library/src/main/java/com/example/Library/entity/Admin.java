package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Lob
    @Column(name = "image",columnDefinition = "MEDIUMBLOB")
    private String image;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "admins_roles",
            joinColumns = @JoinColumn(name = "admin_id",referencedColumnName = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    )
    private Collection<Role>roles;
}
