package com.example.Library.service;

import com.example.Library.dto.CategoryDto;
import com.example.Library.entity.Category;
import com.example.Library.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

   //Admin
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public boolean checkByName(String name) {
        return this.categoryRepository.existsByName(name);
    }

    @Override
    @Transactional
    public Category save(Category category) {
       try {
           Category categorySave=new Category(category.getName());
           return categoryRepository.save(categorySave);
       }
       catch (Exception e)
       {
            e.printStackTrace();
            return null;
       }
    }

    @Override
    public Category getById(Long id) {
        return this.categoryRepository.getById(id);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        Category categoryUpdate=null;
      try {
          categoryUpdate=categoryRepository.getById(category.getId());
          categoryUpdate.setName(category.getName());
          categoryUpdate.setActivated(category.isActivated());
          categoryUpdate.setDeleted(category.isDeleted());
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
       return this.categoryRepository.saveAndFlush(categoryUpdate);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
          Category category=this.categoryRepository.getById(id);
          category.setDeleted(true);
          category.setActivated(false);
          this.categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void enableById(Long id) {
        Category category=this.categoryRepository.getById(id);
        category.setDeleted(false);
        category.setActivated(true);
        this.categoryRepository.save(category);
    }

    @Override
    public List<Category> checkActive() {
        return this.categoryRepository.checkAllActive();
    }


    //Customer
    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }
}
