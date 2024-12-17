package com.tcnguyen.TiCi.service.product;

import com.tcnguyen.TiCi.dto.request.product.ProductReq;
import com.tcnguyen.TiCi.dto.request.product.ProductUpdateReq;
import com.tcnguyen.TiCi.entities.Category;
import com.tcnguyen.TiCi.entities.Product;
import com.tcnguyen.TiCi.repository.category.CategoryRepository;
import com.tcnguyen.TiCi.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductReq req) {

        Category category = Optional.ofNullable(categoryRepository.findByName(req.getCategory().getName()))
                .orElseGet(() -> {
                    Category c = new Category(req.getCategory().getName());
                    return categoryRepository.save(c);
                });
        req.setCategory(category);
        return productRepository.save(createProduct(req,category));
    }

    private Product createProduct(ProductReq req, Category category) {
        return new Product(
                req.getName(),
                req.getBrand(),
                req.getPrice(),
                req.getInventory(),
                req.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with this " + id));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
        log.warn("Product ID : %d has been deleted", id);
    }

    @Override
    public Product updateProduct(ProductUpdateReq request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(() -> new RuntimeException("Product not found with this " + productId));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateReq req) {
        existingProduct.setName(req.getName());
        existingProduct.setBrand(req.getBrand());
        existingProduct.setPrice(req.getPrice());
        existingProduct.setInventory(req.getInventory());
        existingProduct.setDescription(req.getDescription());

        Category category = categoryRepository.findByName(req.getCategory().getName());
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findAllByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findAllByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findAllByName(productName);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String productName) {
        return productRepository.findAllBrandAndName(brand,productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String productName) {
        return productRepository.countByBrandAndName(brand,productName);
    }
}
