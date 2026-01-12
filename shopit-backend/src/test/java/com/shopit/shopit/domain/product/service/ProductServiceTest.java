package com.shopit.shopit.domain.product.service;

import com.shopit.shopit.domain.product.dto.request.CreateProductRequest;
import com.shopit.shopit.domain.product.dto.request.ProductOptionRequest;
import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.domain.product.exception.ProductErrorCode;
import com.shopit.shopit.domain.product.exception.ProductOptionRequiredException;
import com.shopit.shopit.domain.product.repository.ProductRepository;
import com.shopit.shopit.global.common.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_등록_성공() {
        // given
        String name = "name";
        String description = "description text";

        ProductOptionRequest option1 = new ProductOptionRequest(
                "name1", "optionValue1", 1000L, 1, "imageUrl1", 0.1
        );
        ProductOptionRequest option2 = new ProductOptionRequest(
                "name2", "optionValue2", 2000L, 2, "imageUrl2", 0.0
        );

        CreateProductRequest request = new CreateProductRequest(
                name,
                description,
                List.of(option1, option2)
        );

        Product savedProduct = Product.create(name, description, List.of());
        ReflectionTestUtils.setField(savedProduct, "id", 1L);

        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // when
        Long productId = productService.createProduct(request).getProductId();

        // then
        assertThat(productId).isEqualTo(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void 상품_옵션이_없으면_등록에_실패한다() {
        // given
        CreateProductRequest request = new CreateProductRequest(
                "name",
                "description",
                List.of() // 옵션 없음
        );

        // when & then
        assertThatThrownBy(() -> productService.createProduct(request))
                .isInstanceOf(ProductOptionRequiredException.class)
                .satisfies(ex -> {
                    ServiceException se = (ServiceException) ex;
                    assertThat(se.getErrorCode())
                            .isEqualTo(ProductErrorCode.PRODUCT_OPTION_REQUIRED);
                });
        verify(productRepository, never()).save(any());
    }
}
