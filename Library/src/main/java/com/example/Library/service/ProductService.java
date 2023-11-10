package com.example.Library.service;


import com.example.Library.dto.ProductDto;
import com.example.Library.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    // Admin
    public ProductDto findById(Long id);
    public List<ProductDto>findAll();
    public Product save(MultipartFile imageProduct,ProductDto productDto);
    public Product update(MultipartFile imageProduct,ProductDto productDto);
    public void deleteById(Long id);
    public void enabledById(Long id);
    Page<ProductDto> pageProduct(int pageNo,String sortField,String sortDir);
    Page<ProductDto> searchProduct(int pageNo,String sortField,String sortDir,String keyword);

    //Customers
   public List<Product>getAllProduct();
   public List<Product>listViewProduct();
   public Product getProductById(Long id);
   public List<Product>getRelatedProduct(Long categoryId);
   public List<Product>getProductInCategory(Long categoryId);
    public List<Product>filterHighPrice();
    public List<Product>filterLowPrice();
}
