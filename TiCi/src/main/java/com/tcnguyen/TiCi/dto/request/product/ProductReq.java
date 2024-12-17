package com.tcnguyen.TiCi.dto.request.product;

import com.tcnguyen.TiCi.entities.Category;
import com.tcnguyen.TiCi.entities.Image;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductReq {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
