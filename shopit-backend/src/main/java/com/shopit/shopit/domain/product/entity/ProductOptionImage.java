package com.shopit.shopit.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_option_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", nullable = false)
    private ProductOption productOption;

    public static ProductOptionImage create(
            ProductOption productOption,
            String imageUrl
    ) {
        ProductOptionImage image = new ProductOptionImage();
        image.productOption = productOption;
        image.imageUrl = imageUrl;
        return image;
    }
}