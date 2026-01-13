package com.shopit.shopit.domain.product.dto.response;

import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.global.api.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductsPageResponse {

    private List<ProductResponse> products;
    private PageInfo pageInfo;

    public static ProductsPageResponse from(Page<Product> page) {
        return new ProductsPageResponse(
                page.getContent().stream()
                        .map(ProductResponse::from)
                        .toList(),
                PageInfo.from(page)
        );
    }
}
