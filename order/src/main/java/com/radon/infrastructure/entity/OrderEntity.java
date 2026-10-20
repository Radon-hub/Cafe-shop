package com.radon.infrastructure.entity;

import com.radon.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;


    public OrderEntity(String orderNumber,UserEntity user, OrderStatus orderStatus, BigDecimal totalPrice, String paymentId, String idempotencyKey) {
        this.orderNumber = orderNumber;
        this.user = user;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.paymentId = paymentId;
        this.idempotencyKey = idempotencyKey;
    }

}
