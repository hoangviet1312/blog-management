package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categorys")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/create-blog")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/create-blog")
    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/blogs")
    public ModelAndView listBlogs(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<Blog> blogs;
        if (s.isPresent()) {
            blogs = blogService.findAllByBlogNameContaining(s.get(), pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/edit-blog/{id}")
    public ModelAndView editForm(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error-404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-blog")
    public ModelAndView updateCustomer(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("message", "Blog updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-blog/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Blog blog = blogService.findById(id);

        if (blog == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @PostMapping("/delete-blog")
    public String deleteBlog(@ModelAttribute("blog") Blog blog) {
        blogService.remove(blog.getId());
        return "redirect:blogs";
    }

    @GetMapping("/view-blog/{id}")
    public ModelAndView detailInformationBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);

        if (blog == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/view");
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }
}
