package com.example.Library.repository;

import com.example.Library.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    public Admin findByUserName(String userName);
    public boolean existsByUserName(String userName);
}
