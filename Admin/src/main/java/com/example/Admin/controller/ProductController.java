package com.example.Admin.controller;

import com.example.Library.dto.ProductDto;
import com.example.Library.entity.Category;
import com.example.Library.entity.Product;
import com.example.Library.service.CategoryService;
import com.example.Library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/products")
    public String product(Model model, Principal principal)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }
        return pageProducts(1,"costPrice","ASC",model,principal);
    }
    //products/pageNo?sortField=field&sortDir=dir
    @GetMapping("/products/{pageNo}")
    public String pageProducts(@PathVariable(value = "pageNo")int pageNo,
                                @RequestParam(value = "sortField") String sortField,
                                @RequestParam(value = "sortDir") String sortDir,
                               Model model,Principal principal)
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        else {
            Page<ProductDto>productPage=this.productService.pageProduct(pageNo,sortField,sortDir);
            model.addAttribute("title","Manage Product");
            model.addAttribute("size",productPage.getSize());
            model.addAttribute("totalPages",productPage.getTotalPages());
            model.addAttribute("currentPage",pageNo);
            model.addAttribute("sortField",sortField);
            model.addAttribute("sortDir",sortDir);
            model.addAttribute("reverse",sortDir.equalsIgnoreCase("ASC")?"DESC" :"ASC");
            model.addAttribute("products",productPage.getContent());
            return "products";
        }
    }
    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable(value = "pageNo") int pageNo,
                                 @RequestParam(value = "keyword") String keyword,
                                 @RequestParam(value = "sortField") String sortField,
                                 @RequestParam(value = "sortDir") String sortDir,
                                 Model model,
                                 Principal principal
                                 )
    {
            if (principal == null)
            {
                return "redirect:/login";
            }
            else {
                System.out.println(pageNo);
                System.out.println(keyword);
                System.out.println(sortDir);
                System.out.println(sortField);
                Page<ProductDto> productPage=productService.searchProduct(pageNo,sortField,sortDir,keyword);
                model.addAttribute("title","Search Result");
                model.addAttribute("products",productPage.getContent());
                model.addAttribute("currentPage",pageNo);
                model.addAttribute("totalPages",productPage.getTotalPages());
                model.addAttribute("size",productPage.getTotalElements());
                model.addAttribute("keyword",keyword);
                model.addAttribute("sortField",sortField);
                model.addAttribute("sortDir",sortDir);
                model.addAttribute("reverse",sortDir.equalsIgnoreCase("ASC")?"DESC" :"ASC");
                return "result-products";
            }
    }
    @GetMapping("/add-product")
    public String addProduct(Model model,Principal principal)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }
        List<Category> categories=this.categoryService.checkActive();
        model.addAttribute("categories",categories);
        model.addAttribute("product",new ProductDto());
        return "add-product";
    }
    @PostMapping("/save-product")
    public  String saveProduct(@ModelAttribute(value = "product")ProductDto productDto,
                               @RequestParam("imageProduct")MultipartFile imageProduct,
                               RedirectAttributes redirectAttributes, Model model)
    {
        try {
            productService.save(imageProduct,productDto);
            redirectAttributes.addFlashAttribute("success","Save Product Successfully");
        }
        catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("error","Failed to add");
            e.printStackTrace();
        }
        return "redirect:/products";
    }
    @GetMapping("/update-product/{id}")
    public String updateProduct(@PathVariable(value = "id") Long id,Principal principal,Model model)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }
        model.addAttribute("title","Update products");
        List<Category>categories=categoryService.checkActive();

        ProductDto productDto=productService.findById(id);
        model.addAttribute("categories",categories);
        model.addAttribute("productDto",productDto);
        return "update-product";
    }
    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable(value = "id") Long id,
                                @ModelAttribute(value = "productDto") ProductDto productDto,
                                @RequestParam(value = "imageProductDto") MultipartFile imageProductDto,
                                Model model,RedirectAttributes redirectAttributes)
    {
        try {
            productService.update(imageProductDto,productDto);
            redirectAttributes.addFlashAttribute("success","Update successfully");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Failed to update");
        }
        return "redirect:/products";
    }
    @GetMapping("/enabled-product/{id}")
    public String enabledProduct(@PathVariable(value = "id")Long id,RedirectAttributes redirectAttributes,Model model)
    {
        try{
            productService.enabledById(id);
            redirectAttributes.addFlashAttribute("success","Enabled successfully");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Failed to enabled");
        }
        return "redirect:/products";
    }
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable(value = "id")Long id,RedirectAttributes redirectAttributes,Model model)
    {
        try{
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success","Deleted successfully");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Failed to enabled");
        }
        return "redirect:/products";
    }
}
