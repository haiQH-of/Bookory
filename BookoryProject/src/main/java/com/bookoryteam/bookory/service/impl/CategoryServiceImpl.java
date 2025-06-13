package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.Category;
import com.bookoryteam.bookory.repository.CategoryRepository;
import com.bookoryteam.bookory.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findByDeletedFalse() {
        return categoryRepository.findByDeletedFalse();
    }

    @Override
    public void softDelete(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setDeleted(true);
            categoryRepository.save(category);
        }
    }

    // ✅ THÊM MỚI: Hàm đếm số lượng danh mục
    @Override
    public int countCategories() {
        return (int) categoryRepository.count();
    }
}
