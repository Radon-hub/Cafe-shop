package com.radon.infrastructure.entity;

import com.radon.domain.Inventory;
import com.radon.domain.Product;
import com.radon.domain.Warehouse;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false,unique = true)
    private ProductEntity product;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WarehouseEntity wareHouse;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public InventoryEntity() {}

    public InventoryEntity(ProductEntity product,int count,WarehouseEntity wareHouse) {
        this.product = product;
        this.count = count;
        this.wareHouse = wareHouse;
    }

    public static InventoryEntity of(ProductEntity productEntity, Inventory inventory, Warehouse warehouse) {
        return new InventoryEntity(
                productEntity,
                inventory.count(),
                WarehouseEntity.of(warehouse)
        );
    }

}
