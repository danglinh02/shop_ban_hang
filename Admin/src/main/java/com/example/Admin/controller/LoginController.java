package com.example.Admin.controller;

import com.example.Library.dto.AdminDto;
import com.example.Library.entity.Admin;
import com.example.Library.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {
    private AdminService adminService;
    @Autowired
    public LoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("title","Login");
        return "login";
    }
    @GetMapping("/index")
    public String home(Model model, Principal p)
    {
        model.addAttribute("title","Home Page");
        if (p == null)
        {
            return "redirect:/login";
        }
        return "index";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("title","Register");
        model.addAttribute("adminDto",new AdminDto());
        return "register";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model)
    {
        model.addAttribute("title","Forgot Password");
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addAdmin(@Valid @ModelAttribute (value = "adminDto") AdminDto adminDto, BindingResult result,RedirectAttributes redirectAttributes ,Model model)
    {
      try {
          if (result.hasErrors())
          {
              model.addAttribute("adminDto",adminDto);
              return "register";
          }
          String userName=adminDto.getUserName();
          Admin admin=this.adminService.findByUserName(userName);
          if (admin!=null)
          {
              model.addAttribute("adminDto",adminDto);
             redirectAttributes.addFlashAttribute("error","Your email has been registered");
              return "register";
          }
          if(adminDto.getPassword().equals(adminDto.getRepeatPassword()))
          {

              this.adminService.save(adminDto);
              model.addAttribute("adminDto",adminDto);
              redirectAttributes.addFlashAttribute("success","Register successfully");
              return "redirect:/register";
          }
          else {

              model.addAttribute("adminDto",adminDto);
              redirectAttributes.addFlashAttribute("error","Your password maybe wrong! Check again!");
              return "register";
          }

      }
      catch (Exception e)
      {
          redirectAttributes.addFlashAttribute("error","The server has been wrong");
          e.printStackTrace();
      }
        return "register";
    }

}
