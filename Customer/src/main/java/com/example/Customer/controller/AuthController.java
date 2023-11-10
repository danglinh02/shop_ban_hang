package com.example.Customer.controller;

import com.example.Library.dto.CustomerDto;
import com.example.Library.entity.Customer;
import com.example.Library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("customerDto",new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute(value = "customerDto")CustomerDto customerDto, BindingResult result, RedirectAttributes redirectAttributes, Model model)
    {
        try {
            if (result.hasErrors())
            {
                System.out.println("loi cu phap");
                model.addAttribute("customerDto",customerDto);
                return "register";
            }
            Customer customer=customerService.findByUserName(customerDto.getUsername());
            if (customer != null)
            {
                System.out.println("username trung");
               model.addAttribute("username","UserName have been registered");
               model.addAttribute("customerDto",customerDto);
               return "register";
            }
            if (customerDto.getPassword().matches(customerDto.getRepeatPassword()))
            {
                System.out.println("Luu thanh cong");
                CustomerDto customerDtoSave=customerService.save(customerDto);
                model.addAttribute("success","Register successfully");
                return "register";
            }
            else {
                System.out.println("mk khong khop");
                model.addAttribute("customerDto",customerDto);
                model.addAttribute("password","Password is not same");
                return "register";
            }
        }
        catch (Exception e)
        {
            model.addAttribute("error","Server have ran some problems");
            e.printStackTrace();
        }
        return "register";
    }
}
