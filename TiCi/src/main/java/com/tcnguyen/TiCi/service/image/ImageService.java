package com.tcnguyen.TiCi.service.image;

import com.tcnguyen.TiCi.dto.DTOs.ImageDto;
import com.tcnguyen.TiCi.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
