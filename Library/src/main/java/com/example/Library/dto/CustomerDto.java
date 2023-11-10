package com.example.Library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CustomerDto {
    @Size(min=4,max = 15,message = "First name should have 4-15 characters")
    private String firstName;
    @Size(min=4,max = 15,message = "Last name should have 4-15 characters")
    private String lastName;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String username;
    @Size(min = 5,max=20,message = "Password should have 5 - 20 characters")
    private String password;
    @Size(min = 5,max=20,message = "Password should have 5 - 20 characters")
    private String repeatPassword;
}
