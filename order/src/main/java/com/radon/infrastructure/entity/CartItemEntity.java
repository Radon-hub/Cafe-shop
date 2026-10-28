package com.radon.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cart_item_seq")
    @SequenceGenerator(name = "cart_item_seq",sequenceName = "cart_item_seq",allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @Column(name = "product_id",nullable = false,updatable = false)
    private Long productId;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public CartItemEntity(CartEntity cart, Long productId, int quantity) {
        this.cart = cart;
        this.productId = productId;
        this.quantity = quantity;
    }
}
