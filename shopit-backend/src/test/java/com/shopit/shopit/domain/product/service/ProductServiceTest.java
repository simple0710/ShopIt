package com.shopit.shopit.domain.product.service;

import com.shopit.shopit.domain.product.dto.request.CreateProductRequest;
import com.shopit.shopit.domain.product.dto.request.ProductOptionRequest;
import com.shopit.shopit.domain.product.dto.response.ProductsPageResponse;
import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.domain.product.exception.ProductErrorCode;
import com.shopit.shopit.domain.product.exception.ProductOptionRequiredException;
import com.shopit.shopit.domain.product.repository.ProductRepository;
import com.shopit.shopit.global.common.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    void 상품_목록_페이지_조회_성공() {
        // given
        Product product = ProductTestFixture.product();
        Page<Product> page = new PageImpl<>(
                List.of(product),
                PageRequest.of(0, 20),
                1
        );

        given(productRepository.findAll(any(Pageable.class)))
                .willReturn(page);

        // when
        ProductsPageResponse response =
                productService.getProducts(PageRequest.of(0, 20));

        // then
        assertThat(response).isNotNull();
        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getPageInfo()).isNotNull();
    }

    public class ProductTestFixture {

        public static Product product() {
            ProductOption option = ProductOption.create(
                    new ProductOptionRequest(
                            "색상",
                            "BLACK",
                            10000L,
                            1,
                            "imageUrl",
                            0.0
                    )
            );
            option.addImage("test-image.jpg");

            return Product.create(
                    "상품명",
                    "상품 설명",
                    List.of(option)
            );
        }
    }

}
