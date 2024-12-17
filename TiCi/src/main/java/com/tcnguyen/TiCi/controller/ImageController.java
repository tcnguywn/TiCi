package com.tcnguyen.TiCi.controller;

import com.tcnguyen.TiCi.dto.BaseResponse;
import com.tcnguyen.TiCi.dto.DTOs.ImageDto;
import com.tcnguyen.TiCi.entities.Image;
import com.tcnguyen.TiCi.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;



    @PostMapping("/upload")
    public ResponseEntity<BaseResponse> saveImages(@RequestParam List<MultipartFile> files,
                                                   @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new BaseResponse("Upload success !", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new BaseResponse("Upload failed !", e.getMessage()));
        }
    }
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImages(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<BaseResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new BaseResponse("Update success !", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new BaseResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new BaseResponse("Update failed !", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<BaseResponse> deleteImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new BaseResponse("Delete success !", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new BaseResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new BaseResponse("Delete failed !", INTERNAL_SERVER_ERROR));
    }
}
