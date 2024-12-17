package com.tcnguyen.TiCi.controller;

import com.tcnguyen.TiCi.dto.BaseResponse;
import com.tcnguyen.TiCi.dto.request.product.ProductReq;
import com.tcnguyen.TiCi.dto.request.product.ProductUpdateReq;
import com.tcnguyen.TiCi.entities.Product;
import com.tcnguyen.TiCi.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new BaseResponse("success", products));
    }

    @GetMapping("/product/{id}/info")
    public ResponseEntity<BaseResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new BaseResponse("success", product));
    }

    @PostMapping("/product/{id}/create")
    public ResponseEntity<BaseResponse> createProduct(@RequestBody ProductReq product) {
        Product theProduct = productService.addProduct(product);
        return ResponseEntity.ok(new BaseResponse("add product success!", theProduct));
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<BaseResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateReq product) {
        Product newProduct = productService.updateProduct(product, id);
        return ResponseEntity.ok(new BaseResponse("update product success!", newProduct));
    }


}
