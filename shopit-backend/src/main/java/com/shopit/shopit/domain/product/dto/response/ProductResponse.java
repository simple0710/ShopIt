package com.shopit.shopit.domain.product.dto.response;

import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.domain.product.exception.option.ProductOptionNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Long price;
    private String imageUrl;

    public static ProductResponse from(Product product) {
        ProductOption minOption = product.getProductOptions().stream()
                .min(
                        Comparator
                                .comparingLong(ProductOption::getPrice)
                                .thenComparing(ProductOption::getId) // tie-breaker
                )
                .orElseThrow(ProductOptionNotFoundException::new);
        return new ProductResponse(
                product.getId(),
                String.format("%s (%s)",product.getName(), minOption.getOptionValue()),
                minOption.getPrice() - (long) ((double) minOption.getPrice() * minOption.getDiscountRate()),
                minOption.getImages().get(0).getImageUrl()
        );
    }
}
