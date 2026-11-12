package com.radon.infrastructure.entity;


import com.radon.contracts.dto.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reserved")
@Getter
@Setter
public class ReservedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "res_seq")
    @SequenceGenerator(
            name = "res_seq",
            sequenceName = "res_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "order_id",nullable = false)
    private UUID orderId;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "expires_at",nullable = false)
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public ReservedEntity() {}

    public ReservedEntity(Long productId, UUID orderId, int count, Instant expiresAt, ReservationStatus status) {
        this.productId = productId;
        this.orderId = orderId;
        this.count = count;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReservedEntity{" +
                "id=" + id +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", count=" + count +
                ", expiresAt=" + expiresAt +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
