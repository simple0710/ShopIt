package com.shopit.shopit.domain.product.entity;

import com.shopit.shopit.domain.product.dto.request.ProductOptionRequest;
import com.shopit.shopit.type.ProductStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Long id;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @Column(name = "option_value")
    private String optionValue;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "discount_rate")
    private Double discountRate; // 0~1 범위, 예: 10% = 0.1

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(
            mappedBy = "productOption",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Inventory inventory;

    @OneToMany(
            mappedBy = "productOption",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductOptionImage> images = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public static ProductOption create(ProductOptionRequest dto) {
        ProductOption productOption = new ProductOption();
        productOption.optionName = dto.getOptionName();
        productOption.optionValue = dto.getOptionValue();
        productOption.price = dto.getPrice();
        productOption.discountRate = dto.getDiscountRate();
        // 승인
        productOption.status = ProductStatus.APPROVED;

        productOption.inventory = Inventory.create(dto.getQuantity(), productOption);
        return productOption;
    }

    public static ProductOption fromDto(ProductOptionRequest dto) {
        return create(dto);
    }

    public void addImage(String imageUrl) {
        images.add(ProductOptionImage.create(this, imageUrl));
    }

    public void clearImages() {
        images.clear(); // orphanRemoval → DB에서도 삭제
    }

    void setProduct(Product product) {
        this.product = product;
    }
}
