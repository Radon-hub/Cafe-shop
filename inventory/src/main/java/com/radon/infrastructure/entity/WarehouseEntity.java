package com.radon.infrastructure.entity;

import com.radon.domain.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String warehouse;

    @OneToMany(mappedBy = "wareHouse")
    private List<InventoryEntity> inventory;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public WarehouseEntity() {}

    public WarehouseEntity(String warehouse) {
        this.warehouse = warehouse;
    }

    public static WarehouseEntity of(Warehouse warehouse) {
        return new WarehouseEntity(warehouse.warehouse());
    }
}
