package com.angello.services;

import com.angello.api.v1.model.CategoryDTO;

import java.util.List;

/**
 * Created by jt on 9/26/17.
 */
public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
