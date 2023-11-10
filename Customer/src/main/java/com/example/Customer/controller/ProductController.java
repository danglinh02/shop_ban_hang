package com.example.Customer.controller;

import com.example.Library.dto.CategoryDto;
import com.example.Library.dto.ProductDto;
import com.example.Library.entity.Category;
import com.example.Library.entity.Product;
import com.example.Library.service.CategoryService;
import com.example.Library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/products")
    public String products(Model model)
    {
        List<CategoryDto>categoryDtoList=categoryService.getCategoryAndProduct();
        List<Product> products=productService.getAllProduct();
        List<Product>listViewProducts=productService.listViewProduct();
        model.addAttribute("products",products);
        model.addAttribute("viewProduct",listViewProducts);
        model.addAttribute("title","Shop");
        model.addAttribute("categories",categoryDtoList);

        return "shop";
    }
    @GetMapping("/find-product/{id}")
    public String findProduct(@PathVariable(value = "id") Long id,Model model)
    {

           Product product=this.productService.getProductById(id);
           List<CategoryDto>categoryDtoList=categoryService.getCategoryAndProduct();
           Long categoryId=product.getCategory().getId();
           List<Product>products=productService.getRelatedProduct(categoryId);
           model.addAttribute("product",product);
           model.addAttribute("products",products);
           model.addAttribute("categoryDtoList",categoryDtoList);

       return "product-detail";
    }
    @GetMapping("/product-in-category/{id}")
    private String getProductInCategory(@PathVariable(value = "id")Long id, Model model)
    {
        Category category=categoryService.getById(id);
        List<CategoryDto>categories=categoryService.getCategoryAndProduct();
        List<Product>products=productService.getProductInCategory(id);
        model.addAttribute("category",category);
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        return "product-in-category";
    }
    @GetMapping("/high-price")
    public String filterHighPrice(Model model)
    {
        List<Category>categories=categoryService.checkActive();
        List<Product>products=productService.filterHighPrice();
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        return "filter-high-price";
    }
    @GetMapping("low-price")
    public String filterLowPrice(Model model)
    {
        List<Category>categories=categoryService.checkActive();
        List<CategoryDto>categoryDtoList=categoryService.getCategoryAndProduct();
        List<Product>products=productService.filterLowPrice();
        model.addAttribute("categories",categories);
        model.addAttribute("categoryDtoList",categoryDtoList);
        model.addAttribute("products",products);
        return "filter-low-price";

    }

}
