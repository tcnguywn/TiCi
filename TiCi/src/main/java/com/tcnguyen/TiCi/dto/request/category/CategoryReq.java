package com.tcnguyen.TiCi.dto.request.category;

import com.tcnguyen.TiCi.entities.Product;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryReq {
    private Long id;
    private String name;
    private List<Product> products;
}
