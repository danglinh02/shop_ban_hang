package com.example.Library.service;

import com.example.Library.dto.ProductDto;
import com.example.Library.entity.Product;
import com.example.Library.repository.ProductRepository;
import com.example.Library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageUpload imageUpload;

    @Override
    public ProductDto findById(Long id) {
        Product product=this.productRepository.getById(id);
        ProductDto productDto=new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCurrentQuality(product.getCurrentQuality());
        productDto.setCategory(product.getCategory());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.isDeleted());
        productDto.setActivated(product.isActivated());
        return productDto;

    }

    @Override
    public List<ProductDto> findAll() {
        List<Product>productList=this.productRepository.findAll();
        List<ProductDto>productDtoList=transfer(productList);
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct,ProductDto productDto) {
      try {
          Product product=new Product();
          if (imageProduct == null)
          {
              product.setImage(null);
          }
          else {
              if (imageUpload.upLoadImage(imageProduct))
              {
                  System.out.println("Upload successfully");
              }
              imageUpload.upLoadImage(imageProduct);
              product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
          }
          product.setName(productDto.getName());
          product.setDescription(productDto.getDescription());
          product.setCategory(productDto.getCategory());
          product.setCostPrice(productDto.getCostPrice());
          product.setSalePrice(productDto.getSalePrice());
          product.setCurrentQuality(productDto.getCurrentQuality());
          product.setActivated(true);
          product.setDeleted(false);
          return productRepository.save(product);
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
        return null;
    }

    @Override
    public Product update(MultipartFile imageProduct,ProductDto productDto) {
        try{

            Product product=productRepository.getById(productDto.getId()) ;
            if (imageProduct ==null)
            {
                product.setImage(null);
            }
            else {
                if (imageUpload.checkExisted(imageProduct)== false)
                {

                    imageUpload.upLoadImage(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalePrice());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuality(productDto.getCurrentQuality());
            product.setCategory(productDto.getCategory());
            System.out.println(product.getName()+" "+product.getCategory());
            return productRepository.save(product);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        Product product=productRepository.getById(id);
        product.setDeleted(true);
        product.setActivated(false);
        productRepository.save(product);

    }

    @Override
    public void enabledById(Long id) {
        Product product=productRepository.getById(id);
        product.setDeleted(false);
        product.setActivated(true);
        productRepository.save(product);
    }

    @Override
    public Page<ProductDto> pageProduct(int pageNo,String sortField,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo-1,5,sort);
        List<ProductDto>products=transfer(this.productRepository.findAll());
        Page<ProductDto>productPage=toPage(products,pageable);
         return productPage;
    }

    @Override
    public Page<ProductDto> searchProduct(int pageNo,String sortField,String sortDir,String keyWord) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1,5,sort);
        List<ProductDto>productDtoList=transfer(this.productRepository.searchProductList(keyWord));
        Page<ProductDto>products=toPage(productDtoList,pageable);
        return products;
    }
    private Page toPage(List<ProductDto>list,Pageable pageable)
    {
        if (pageable.getOffset() >= list.size())
        {
            return Page.empty();
        }
        else {
            int startIndex=(int)pageable.getOffset();
            int endIndex=((pageable.getOffset() + pageable.getPageSize()) > list.size())? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
            List subList=list.subList(startIndex,endIndex);
            return new PageImpl(subList,pageable,list.size());
        }
    }
    private List<ProductDto>transfer(List<Product> products){
        List<ProductDto>productDtoList=new ArrayList<>();
        for(Product product:products)
        {
            ProductDto productDto=new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuality(product.getCurrentQuality());
            productDto.setCategory(product.getCategory());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.isDeleted());
            productDto.setActivated(product.isActivated());
           productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    @Override
    public List<Product> listViewProduct() {
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getRelatedProduct(Long categoryId) {
        return this.productRepository.getRelatedProducts(categoryId);
    }

    @Override
    public List<Product> getProductInCategory(Long categoryId) {
        return this.productRepository.getProductsInCategory(categoryId);
    }

    @Override
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }

    @Override
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }
}
