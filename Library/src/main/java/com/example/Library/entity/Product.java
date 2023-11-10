package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "cost_price")
    private double costPrice;
    @Column(name = "sale_price")
    private double salePrice;
    @Column(name = "current_quality")
    private int currentQuality;
    @Lob
    @Column(name = "image",columnDefinition = "MEDIUMBLOB")
    private String image;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "is_activated")
    private boolean isActivated;
}
