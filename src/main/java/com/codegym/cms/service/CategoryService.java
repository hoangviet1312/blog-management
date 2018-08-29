package com.codegym.cms.service;

import com.codegym.cms.model.Category;

public interface CategoryService {

    Iterable<Category> findAll();

    Category findById(Long id);

    void save(Category category);

    void remove(Long id);
}
