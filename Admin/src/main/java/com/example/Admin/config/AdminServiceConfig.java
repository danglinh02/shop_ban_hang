package com.example.Admin.config;

import com.example.Library.entity.Admin;
import com.example.Library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class AdminServiceConfig implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    public AdminServiceConfig() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin=adminRepository.findByUserName(username);
        if (admin==null)
        {
            throw new UsernameNotFoundException("Could not find username");
        }
        else {
            return new User(admin.getUserName(), admin.getPassword(),admin.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
        }
    }
}
