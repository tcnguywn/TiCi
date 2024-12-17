package com.tcnguyen.TiCi.controller;

import com.tcnguyen.TiCi.dto.BaseResponse;
import com.tcnguyen.TiCi.dto.request.category.CategoryReq;
import com.tcnguyen.TiCi.dto.request.category.CategoryUpdateReq;
import com.tcnguyen.TiCi.entities.Category;
import com.tcnguyen.TiCi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return  ResponseEntity.ok(new BaseResponse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new BaseResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addCategory(@RequestBody Category name) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return  ResponseEntity.ok(new BaseResponse("Success", theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new BaseResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<BaseResponse> getCategoryById(@PathVariable Long id){
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return  ResponseEntity.ok(new BaseResponse("Found", theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new BaseResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<BaseResponse> getCategoryByName(@PathVariable String name){
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new BaseResponse("Found", theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new BaseResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return  ResponseEntity.ok(new BaseResponse("Found", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new BaseResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<BaseResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new BaseResponse("Update success!", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new BaseResponse(e.getMessage(), null));
        }
    }
}
