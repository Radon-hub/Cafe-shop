package com.radon.infrastructure.entity;

import com.radon.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(
                        name = "idx_category_name",
                        columnList = "name"
                )
        }
)
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cat_seq")
    @SequenceGenerator(
            name = "cat_seq",
            sequenceName = "cat_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products = new ArrayList<>();

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }

    public static CategoryEntity of(Category category) {
        return new CategoryEntity(category.name());
    }

}
