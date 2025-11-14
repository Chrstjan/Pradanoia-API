package Pradanoia.services;

import Pradanoia.payloads.entities.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer id);

    void deleteCategory(Integer id);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Integer id);
}
