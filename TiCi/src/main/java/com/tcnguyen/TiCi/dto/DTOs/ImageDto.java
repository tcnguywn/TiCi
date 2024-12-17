package com.tcnguyen.TiCi.dto.DTOs;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;

}