package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categorys")
    public ModelAndView ShowListBlog() {
        Iterable<Category> categories = categoryService.findAll();

        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", category);
        modelAndView.addObject("message", "Category created compliment");
        return modelAndView;
    }

    @GetMapping("/update-category/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        if (category == null) {
            return new ModelAndView("/error.404");
        }
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/update-category")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", category);
        modelAndView.addObject("message", "Category updated compliment");
        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        if (category == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/delete-category")
    public String deleteCategory(@ModelAttribute("category") Category category) {
        categoryService.remove(category.getId());

        return "redirect:categorys";
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView detailInformationCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        if (category == null) {
            return new ModelAndView("/error.404");
        }

        Iterable<Blog> blogs = blogService.findAllByCategory(category);

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category", category);
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }
}
