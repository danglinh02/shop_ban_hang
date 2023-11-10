package com.example.Customer.controller;

import com.example.Library.entity.Customer;
import com.example.Library.entity.Order;
import com.example.Library.entity.ShoppingCart;
import com.example.Library.service.CustomerService;
import com.example.Library.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal)
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        String username= principal.getName();
        Customer customer=customerService.findByUserName(username);
        if (customer.getPhoneNumber() == null || customer.getAddress() == null || customer.getCity()==null|| customer.getCountry()==null)
        {
                model.addAttribute("customer",customer);
                model.addAttribute("error","You must fill the information after checkout");
                return "account";
        }
        else if (customer.getPhoneNumber().trim() == null || customer.getAddress().trim() ==null || customer.getCity().trim()==null|| customer.getCountry().trim()==null)
        {
                model.addAttribute("customer",customer);
                model.addAttribute("error","You must fill the information after checkout");
                return "account";
        }
        else {
                model.addAttribute("customer",customer);
                ShoppingCart cart=customer.getShoppingCart();
                model.addAttribute("cart",cart);
        }

        return "checkout";

    }
    @GetMapping("/order")
    public  String order(Principal principal,Model model)
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        String username=principal.getName();
        Customer customer=customerService.findByUserName(username);
        List<Order>orders=customer.getOrders();
        model.addAttribute("orders",orders);
        return "order";
    }
    @GetMapping("/save-order")
    public String saveOrder(Principal principal)
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        String username=principal.getName();
        Customer customer=customerService.findByUserName(username);
        ShoppingCart shoppingCart=customer.getShoppingCart();
        orderService.saveOder(shoppingCart);
        return "redirect:/order";
    }
    @GetMapping("/accept-order/{id}")
    public String accept(@PathVariable(value = "id")Long id, RedirectAttributes redirectAttributes)
    {
        try {
            orderService.acceptOrder(id);
            redirectAttributes.addFlashAttribute("success","Accepted successfully");
        }
        catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("error","Server have ran some problems");

            e.printStackTrace();
        }
        return "redirect:/order";
    }
    @GetMapping("/cancel-order/{id}")
    public String cancel(@PathVariable(value = "id")Long id, RedirectAttributes redirectAttributes)
    {
        try {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success","Canceled successfully");
            redirectAttributes.addFlashAttribute("id",id);
        }
        catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("error","Server have ran some problems");

            e.printStackTrace();
        }
        return "redirect:/order";
    }
}
