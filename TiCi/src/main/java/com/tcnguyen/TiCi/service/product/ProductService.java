package com.tcnguyen.TiCi.service.product;

import com.tcnguyen.TiCi.dto.request.product.ProductReq;
import com.tcnguyen.TiCi.dto.request.product.ProductUpdateReq;
import com.tcnguyen.TiCi.entities.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductReq req);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateReq request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String productName);
    List<Product> getProductByBrandAndName(String brand, String productName);
    Long countProductsByBrandAndName(String brand, String productName);
}
