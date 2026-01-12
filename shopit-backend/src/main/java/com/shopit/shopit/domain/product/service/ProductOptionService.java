package com.shopit.shopit.domain.product.service;

import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.domain.product.exception.option.ProductOptionNotFoundException;
import com.shopit.shopit.domain.product.repository.ProductOptionRepository;
import com.shopit.shopit.global.config.FileProperties;
import com.shopit.shopit.global.file.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final FileStorageService fileStorageService;
    private final FileProperties fileProperties;

    @Transactional
    public void addImages(Long optionId, List<MultipartFile> images) {
        ProductOption option = productOptionRepository.findById(optionId)
                .orElseThrow(ProductOptionNotFoundException::new);

        String productOptionDir = fileProperties.getProductOption();

        for (MultipartFile image : images) {
            String imagePath = fileStorageService.saveImage(image, productOptionDir);
            option.addImage(imagePath);
        }
    }

}
