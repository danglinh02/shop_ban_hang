package com.example.Library.service;

import com.example.Library.dto.AdminDto;
import com.example.Library.entity.Admin;
import com.example.Library.repository.AdminRepository;
import com.example.Library.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Admin findByUserName(String userName) {
        return this.adminRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public Admin save(AdminDto adminDto) {
        Admin admin=new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUserName(adminDto.getUserName());
        admin.setPassword(bCryptPasswordEncoder.encode(adminDto.getPassword()));
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return this.adminRepository.save(admin);
    }

    @Override
    public boolean checkUserName(String userName) {
        return this.adminRepository.existsByUserName(userName);
    }
}
