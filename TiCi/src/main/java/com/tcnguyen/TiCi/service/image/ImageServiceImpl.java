package com.tcnguyen.TiCi.service.image;

import com.tcnguyen.TiCi.dto.DTOs.ImageDto;
import com.tcnguyen.TiCi.entities.Image;
import com.tcnguyen.TiCi.entities.Product;
import com.tcnguyen.TiCi.repository.image.ImageRepository;
import com.tcnguyen.TiCi.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find image with this id : " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new RuntimeException("Cannot find image with this id : " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = null;
        try {
            product = productService.getProductById(productId);
        } catch (Exception e) {
            throw new RuntimeException("cannot find product with this id : " + productId);
        }
        List<ImageDto> savedImageDtos = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                image.setDownloadUrl(buildDownloadUrl + image.getId());

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                ImageDto imageDto = new ImageDto();
                imageDto.setId(image.getId());
                imageDto.setFileName(image.getFileName());
                imageDto.setDownloadUrl(image.getDownloadUrl());

                savedImageDtos.add(imageDto);
            }
            catch (SQLException | IOException e ) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));

            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
