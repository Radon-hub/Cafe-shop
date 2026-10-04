package com.radon.infrastructure.repository.specifications;

import com.radon.infrastructure.entity.ProductEntity;
import com.radon.presentation.dto.ProductSearchFilterRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {
    public static Specification<ProductEntity> productSearchFilter(ProductSearchFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(filter.name() != null && !filter.name().isBlank()){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%"+filter.name().toLowerCase()+"%"
                        )
                );
            }

            if(filter.category() != null){
                for(String category : filter.category()){
                    predicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("category").get("name")),
                                    category.toLowerCase()
                            )
                    );
                }
            }

            if(filter.minPrice() != null){
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("price"),
                                filter.minPrice()
                        )
                );
            }

            if(filter.maxPrice() != null){
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("price"),
                                filter.maxPrice()
                        )
                );
            }

            if(filter.maxWeight() != null){
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("weight"),
                                filter.maxWeight()
                        )
                );
            }

            if(filter.minWeight() != null){
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("weight"),
                                filter.minWeight()
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
