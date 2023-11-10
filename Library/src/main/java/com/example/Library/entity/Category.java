package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "categories",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "is_activated")
    private boolean isActivated;
    public Category(String name)
    {
        this.name=name;
        this.isDeleted=false;
        this.isActivated=true;
    }
}
