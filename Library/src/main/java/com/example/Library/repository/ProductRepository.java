package com.example.Library.repository;

import com.example.Library.dto.ProductDto;
import com.example.Library.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    //Admin
    @Query("select s from Product s")
    public Page<Product>pageProduct(Pageable pageable);
    @Query("select s from Product s where s.description like %?1% or s.name like %?1%")
    public Page<Product>searchProduct(String keyWord,Pageable pageable);
    @Query("select s from Product s where s.description like %?1% or s.name like %?1%")
    public List<Product> searchProductList(String keyWord);


    //Customers
    @Query("select s from Product s where s.isActivated =true and s.isDeleted = false")
    public List<Product>getAllProduct();
    @Query(value = "select * from products p where p.is_deleted = false and p.is_activated = true order by rand() asc limit 4 ", nativeQuery = true)
    List<Product> listViewProducts();


    @Query(value = "select p.* from products p inner join categories c on c.category_id = p.category_id where p.category_id = ?1", nativeQuery = true)
    List<Product> getRelatedProducts(Long categoryId);


    @Query("select p from Product p inner join Category c on c.id = p.category.id where c.id = ?1 and p.isDeleted = false and p.isActivated = true")
    List<Product> getProductsInCategory(Long categoryId);
    @Query("select p from Product p where p.isDeleted = false and p.isActivated=true order by p.costPrice desc")
    List<Product>filterHighPrice();

    @Query("select p from Product p where p.isDeleted = false and isActivated =true order by p.costPrice asc")
    List<Product>filterLowPrice();
}
