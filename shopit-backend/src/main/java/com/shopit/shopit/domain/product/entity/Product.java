package com.shopit.shopit.domain.product.entity;

import com.shopit.shopit.type.ProductStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(0)"
    )
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductOption> productOptions = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public static Product create(String name, String description, List<ProductOption> options) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.status = ProductStatus.APPROVED;
        if (options != null) {
            options.forEach(product::addOption);
        }
        return product;
    }

    public void addOption(ProductOption option) {
        option.setProduct(this);
        this.productOptions.add(option);
    }

    public void changeStatus(ProductStatus newStatus) {

        if (this.status == newStatus) return;

        validateStatusTransition(newStatus);

        this.status = newStatus;

        if (newStatus == ProductStatus.STOPPED) {
            this.productOptions.forEach(ProductOption::stop);
        }
    }

    private void validateStatusTransition(ProductStatus newStatus) {

//        if (this.status == ProductStatus.SOLD_OUT) {
//            throw new ProductNotAvailableException();
//        }
    }
}
