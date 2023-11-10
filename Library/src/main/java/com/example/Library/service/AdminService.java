package com.example.Library.service;

import com.example.Library.dto.AdminDto;
import com.example.Library.entity.Admin;

public interface AdminService {
    public Admin findByUserName(String userName);
    public Admin save(AdminDto adminDto);
    public boolean checkUserName(String userName);
}
