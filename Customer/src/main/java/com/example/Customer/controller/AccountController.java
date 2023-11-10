package com.example.Customer.controller;

import com.example.Library.entity.City;
import com.example.Library.entity.Customer;
import com.example.Library.service.CityService;
import com.example.Library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.lang.model.element.NestingKind;
import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/account")
    public String accountHome(Model model, Principal principal)
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        String username=principal.getName();
        Customer customer=customerService.findByUserName(username);
        model.addAttribute("customer",customer);
        return "account";
    }
    @GetMapping("/update-info")
    public String updateCustomer(
            @ModelAttribute("customer")Customer customer,
            RedirectAttributes redirectAttributes,
            Principal principal)
    {
        if (principal ==null)
        {
            return "redirect:/login";
        }
        Customer customerSaved=customerService.saveInfo(customer);
       redirectAttributes.addFlashAttribute("customer",customerSaved);
        return "redirect:/account";

    }
}
