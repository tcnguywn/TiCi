package com.tcnguyen.TiCi.dto.request.category;

import com.tcnguyen.TiCi.entities.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryUpdateReq {
    private String name;
    private List<Product> products;
}
