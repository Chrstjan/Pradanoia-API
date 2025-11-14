package Pradanoia.services.impl;

import Pradanoia.entities.Category;
import Pradanoia.exceptions.ResourceNotFoundException;
import Pradanoia.payloads.entities.CategoryDto;
import Pradanoia.repositories.CategoryRepo;
import Pradanoia.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    private Category DtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDto CategoryToDto(Category category) {
       return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.DtoToCategory(categoryDto);
        Category savedCategory = categoryRepo.save(category);
        return this.CategoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

        category.setTitle(categoryDto.getTitle());

        Category updatedCategory = categoryRepo.save(category);
        return this.CategoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
        categoryRepo.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();

        return categories.stream().map(cat -> this.CategoryToDto(cat)).toList();
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
        return this.CategoryToDto(category);
    }
}
