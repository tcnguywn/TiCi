package com.tcnguyen.TiCi.service.category;

import com.tcnguyen.TiCi.dto.request.category.CategoryUpdateReq;
import com.tcnguyen.TiCi.entities.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);


}
