package com.shopit.shopit.domain.product.controller;

import com.shopit.shopit.domain.product.service.ProductOptionService;
import com.shopit.shopit.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/product-options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping(
            value = "/{optionId}/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<?>> uploadProductOptionImages(
            @PathVariable Long optionId,
            @RequestPart List<MultipartFile> images
    ) {
        productOptionService.addImages(optionId, images);
        return ResponseEntity.ok(
                ApiResponse.success(null)
        );
    }

}