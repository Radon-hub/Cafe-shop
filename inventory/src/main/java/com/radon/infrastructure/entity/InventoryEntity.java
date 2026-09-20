package com.radon.infrastructure.entity;

import com.radon.domain.Inventory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "inventory")
@Getter
@Setter
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "inv_seq")
    @SequenceGenerator(
            name = "inv_seq",
            sequenceName = "inv_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false)
    private int count;

    @JoinColumn(name = "product_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductEntity product;

    private String wareHouse;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public InventoryEntity() {}

    public InventoryEntity(int count,String wareHouse) {
        this.count = count;
        this.wareHouse = wareHouse;
    }

    public static InventoryEntity of(Inventory inventory) {
        return new InventoryEntity(
                inventory.count(),
                inventory.wareHouse()
        );
    }

}
