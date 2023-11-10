package com.example.Library.service;

import com.example.Library.dto.CategoryDto;
import com.example.Library.entity.Category;
import com.example.Library.entity.Product;

import java.util.List;

public interface CategoryService {
 //Admin
   public List<Category>findAll();
    public boolean checkByName(String name);
    public Category save(Category category);
    public Category getById(Long id);
    public Category update(Category category);
    public void deleteById(Long id);
    public void enableById(Long id);
    public List<Category> checkActive();


    //Customer
  public List<CategoryDto>getCategoryAndProduct();

}
