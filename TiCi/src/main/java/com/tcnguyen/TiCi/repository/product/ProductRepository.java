package com.tcnguyen.TiCi.repository.product;

import com.tcnguyen.TiCi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findAllByCategoryNameAndBrand(String category, String brand);

    List<Product> findAllByName(String productName);

    List<Product> findAllBrandAndName(String brand, String productName);

    Long countByBrandAndName(String brand, String productName);
}
