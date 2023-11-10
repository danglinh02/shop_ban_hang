package com.example.Customer.controller;

import com.example.Customer.config.CustomerConfiguration;
import com.example.Library.dto.ProductDto;
import com.example.Library.entity.Category;
import com.example.Library.entity.Customer;
import com.example.Library.entity.Product;
import com.example.Library.entity.ShoppingCart;
import com.example.Library.service.CategoryService;
import com.example.Library.service.CustomerService;
import com.example.Library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;
    @GetMapping({"/","/index"})
    public String home(Model model, Principal principal, HttpSession session)
    {
        if (principal != null){

            Customer customer= customerService.findByUserName(principal.getName());
            session.setAttribute("username",customer.getFirstName() + " " +customer.getLastName());
            ShoppingCart cart=customer.getShoppingCart();
            if (cart !=null)
            {
                session.setAttribute("totalItems",cart.getTotalItems());
            }
        }
        else {
            return "redirect:/login";
        }
        return "home";
    }
    @GetMapping("/home")
    public String index(Model model)
    {
        List<Category>categories=categoryService.checkActive();
        List<ProductDto>productDtoList=productService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("products",productDtoList);
        model.addAttribute("title","Index");
        return "index";
    }
}
