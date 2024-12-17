package com.tcnguyen.TiCi.repository.product;

import com.tcnguyen.TiCi.entities.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllByCategory(String category);
}
