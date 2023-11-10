package com.example.Customer.controller;

import com.example.Library.entity.*;
import com.example.Library.service.CustomerService;
import com.example.Library.service.OrderService;
import com.example.Library.service.ProductService;
import com.example.Library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.SpringCglibInfo;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }
        String username= principal.getName();
        Customer customer=customerService.findByUserName(username);
        ShoppingCart shoppingCart=customer.getShoppingCart();

        if (shoppingCart == null)
        {
            model.addAttribute("check","No item in your cart");
        }
        else {

            model.addAttribute("shoppingCart",shoppingCart);
            session.setAttribute("totalItems",shoppingCart.getTotalItems());
            double subTotal=shoppingCart.getTotalPrice();
            model.addAttribute("subTotal",subTotal);
            model.addAttribute("shoppingCart",shoppingCart);
        }
        return "cart";
    }
    @PostMapping("/add-to-cart")
    public String addItemToCart(
                                @RequestParam(value = "id") Long productId,
                                @RequestParam(value = "quantity",required = false,defaultValue = "1") int quantity,
                                Principal principal,
                                Model model,
                                HttpServletRequest request


    )
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        Product product=productService.getProductById(productId);
        String username=principal.getName();
        Customer customer=customerService.findByUserName(username);
        ShoppingCart cart=shoppingCartService.addItemToCart(product,quantity,customer);
        return "redirect:"+request.getHeader("Referer");
    }
    @RequestMapping(value = "/update-cart",method = RequestMethod.POST,params = "action=update")
    public String updateCart(
            @RequestParam("quantity") int quantity,
            @RequestParam("id")Long id,
            Model model,
            Principal principal
    )
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        else {
            String username=principal.getName();
            Customer customer=customerService.findByUserName(username);
            Product product=this.productService.getProductById(id);
            ShoppingCart cart=this.shoppingCartService.updateItemInCart(product,quantity,customer);
            model.addAttribute("shoppingCart",cart);
            return "redirect:/cart";
        }
    }
    @RequestMapping(value = "/update-cart",method = RequestMethod.POST,params = "action=delete")
    public String deleteFromCart(
            @RequestParam("id")Long productId,
            Model model,
            Principal principal
    )
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        else {
            String username=principal.getName();
            Customer customer=customerService.findByUserName(username);
            Product product=productService.getProductById(productId);
            ShoppingCart cart=shoppingCartService.deleteItemFromCart(product,customer);
            model.addAttribute("shoppingCart",cart);
            return "redirect:/cart";
        }
    }

}
