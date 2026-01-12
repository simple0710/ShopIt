package com.shopit.shopit.domain.product.service;

import com.shopit.shopit.domain.product.dto.request.ProductOptionRequest;
import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.domain.product.exception.option.ProductOptionImageFileRequiredException;
import com.shopit.shopit.domain.product.exception.option.ProductOptionImageStorageException;
import com.shopit.shopit.domain.product.exception.option.ProductOptionNotFoundException;
import com.shopit.shopit.domain.product.repository.ProductOptionRepository;
import com.shopit.shopit.global.config.FileProperties;
import com.shopit.shopit.global.file.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductOptionServiceTest {

    @InjectMocks
    private ProductOptionService productOptionService;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private FileProperties fileProperties;

    @Test
    void 이미지_저장_성공() {
        // given
        Long optionId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        List<MultipartFile> images = List.of(image);

        ProductOption option = ProductOption.fromDto(
                new ProductOptionRequest(
                        "name",
                        "optionValue",
                        1000L,
                        1,
                        "stored-image.png",
                        0.0
                )
        );
        given(productOptionRepository.findById(optionId))
                .willReturn(Optional.of(option));

        given(fileProperties.getProductOption())
                .willReturn("product/options");

        given(fileStorageService.saveImage(any(MultipartFile.class), anyString()))
                .willReturn("stored-image.png");

        // when
        productOptionService.addImages(optionId, images);

        // then
        verify(productOptionRepository).findById(optionId);
        verify(fileStorageService).saveImage(image, "product/options");
    }

    @Test
    void 옵션이_존재하지_않으면_이미지_저장에_실패한다() {
        // given
        Long optionId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        List<MultipartFile> images = List.of(image);

        given(productOptionRepository.findById(optionId))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() ->
                productOptionService.addImages(optionId, images)
        )
                .isInstanceOf(ProductOptionNotFoundException.class);

        verify(fileStorageService, never())
                .saveImage(any(), anyString());
    }

    @Test
    void 이미지_파일이_비어있으면_저장에_실패한다() {
        // given
        Long optionId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        List<MultipartFile> images = List.of(image);

        ProductOption option = ProductOption.fromDto(
                new ProductOptionRequest(
                        "name",
                        "optionValue",
                        1000L,
                        1,
                        "stored-image.png",
                        0.0
                )
        );

        given(productOptionRepository.findById(optionId))
                .willReturn(Optional.of(option));

        given(fileProperties.getProductOption())
                .willReturn("product/options");

        // 🔥 핵심: FileStorageService에서 예외가 발생했다고 가정
        given(fileStorageService.saveImage(any(), anyString()))
                .willThrow(new ProductOptionImageFileRequiredException());

        // when & then
        assertThatThrownBy(() ->
                productOptionService.addImages(optionId, images)
        )
                .isInstanceOf(ProductOptionImageFileRequiredException.class);
    }

    @Test
    void 이미지_저장_중_오류가_발생하면_예외가_발생한다() {
        // given
        Long optionId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        List<MultipartFile> images = List.of(image);

        ProductOption option = ProductOption.fromDto(
                new ProductOptionRequest(
                        "name",
                        "optionValue",
                        1000L,
                        1,
                        "stored-image.png",
                        0.0
                )
        );

        given(productOptionRepository.findById(optionId))
                .willReturn(Optional.of(option));

        given(fileProperties.getProductOption())
                .willReturn("product/options");

        given(fileStorageService.saveImage(any(), anyString()))
                .willThrow(new ProductOptionImageStorageException());

        // when & then
        assertThatThrownBy(() ->
                productOptionService.addImages(optionId, images)
        )
                .isInstanceOf(ProductOptionImageStorageException.class);
    }
}