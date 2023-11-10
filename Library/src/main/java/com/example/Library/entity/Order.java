package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "delivery_date")
    private Date deliveryDate;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "shipping_fee")
    private double shippingFee;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "notes")
    private String notes;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderDetail>orderDetailList;

}
