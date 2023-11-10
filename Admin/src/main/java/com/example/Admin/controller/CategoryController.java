package com.example.Admin.controller;

import com.example.Library.entity.Category;
import com.example.Library.repository.CategoryRepository;
import com.example.Library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public String categories(Model model, Principal p)
    {
        if (p==null)
        {
            return "redirect:/login";
        }
        List<Category>categories=categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("size",categories.size());
        model.addAttribute("title","Category");
        return "categories";
    }
    @GetMapping("/addForm-category")
    public String addFormCategory(Model model)
    {
        model.addAttribute("categoryNew",new Category());
        return "add-categories";
    }
    @PostMapping("/save-category")
    public String save(@ModelAttribute(value = "categoryNew") Category category, RedirectAttributes redirectAttributes, Model model)
    {
        try {
            boolean f=this.categoryService.checkByName(category.getName());
           if (f)
           {
               redirectAttributes.addFlashAttribute("failed","Name already exist!");
           }
           else {
               this.categoryService.save(category);
               redirectAttributes.addFlashAttribute("success","Added Successfully");
           }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed","Failed");
        }
        return "redirect:/categories";
    }
    @GetMapping("/editForm/{id}")
    public String edit(@PathVariable (value = "id") Long id,RedirectAttributes redirectAttributes,Model model)
    {
        try {
            Category category=this.categoryService.getById(id);
            if(category !=null)
            {
                model.addAttribute("categoryNew",category);
                return "update-categories";
            }
            else {
                redirectAttributes.addFlashAttribute("error","ID already exist!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addAttribute("error","Error on server");
        }
        return "redirect:/categories";
    }
    @PostMapping("/update-category")
    public String updateCategory(@ModelAttribute(value = "categoryNew") Category category,RedirectAttributes redirectAttributes)
    {
        try {
            boolean f=this.categoryService.checkByName(category.getName());
            if (f)
            {
                redirectAttributes.addFlashAttribute("failed","Name already exist!");
            }
            else {
                this.categoryService.update(category);
                redirectAttributes.addFlashAttribute("success","Update Successfully");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed","Failed");
        }
        return "redirect:/categories";
    }
    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable(value = "id") Long id,RedirectAttributes redirectAttributes,Model model)
    {
        try {
            Category category=this.categoryService.getById(id);
            if (category !=null)
            {
                this.categoryService.deleteById(id);
                redirectAttributes.addFlashAttribute("success","Deleted Successfully");
            }
            else {
                redirectAttributes.addFlashAttribute("failed","ID already exist!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
           redirectAttributes.addFlashAttribute("failed","Error Server");
        }
        return "redirect:/categories";

    }
    @GetMapping("/enable-category/{id}")
    public String enable(@PathVariable(value = "id") Long id,RedirectAttributes redirectAttributes)
    {
        try {
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success","Enabled successfully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed","Failed to enabled");
        }
        return "redirect:/categories";
    }




}
