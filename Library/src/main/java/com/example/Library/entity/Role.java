package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(
            name = "customers_roles",
            joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    )
    private List<Customer> customers;
    public Role(String name) {
        this.name = name;
    }
}
