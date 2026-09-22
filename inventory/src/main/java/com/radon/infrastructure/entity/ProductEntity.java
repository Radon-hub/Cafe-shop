package com.radon.infrastructure.entity;


import com.radon.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_category", columnList = "category_id"),
                @Index(name = "idx_product_name", columnList = "name")
        }
)
@Getter
@Setter
public class ProductEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "prod_seq")
        @SequenceGenerator(
                name = "prod_seq",
                sequenceName = "prod_seq",
                allocationSize = 1
        )
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String description;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "category_id", nullable = false)
        private CategoryEntity category;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "inventory_id")
        private InventoryEntity inventory;

        @Column(nullable = false)
        private BigDecimal price;

        @Column(nullable = false)
        private BigDecimal weight;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false)
        private Instant createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at",nullable = false)
        private Instant updatedAt;

        public ProductEntity() {}

        public ProductEntity(Long id,String name, String description, CategoryEntity category, BigDecimal price, BigDecimal weight) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.category = category;
                this.price = price;
                this.weight = weight;
        }
        public ProductEntity(String name, String description, CategoryEntity category, BigDecimal price, BigDecimal weight) {
                this.name = name;
                this.description = description;
                this.category = category;
                this.price = price;
                this.weight = weight;
        }

        public static ProductEntity of(Product product) {
                return new ProductEntity(
                        product.name(),
                        product.description(),
                        CategoryEntity.of(product.category()),
                        product.price(),
                        product.weight()
                );
        }
}
