package com.example.Library.repository;

import com.example.Library.dto.CategoryDto;
import com.example.Library.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    //Admin
    public boolean existsByName(String name);
    @Query("select c from Category c where c.isActivated = true and c.isDeleted = false")
    public List<Category> checkAllActive();

    //Customers
    @Query("select new com.example.Library.dto.CategoryDto(c.id,c.name,count(p.category.id)) from Category c inner join Product p on p.category.id = c.id "+
        "where c.isDeleted=false and c.isActivated=true group by c.id")
    public List<CategoryDto>getCategoryAndProduct();
}
