package com.example.Library.service;

import com.example.Library.entity.CartItem;
import com.example.Library.entity.Customer;
import com.example.Library.entity.Product;
import com.example.Library.entity.ShoppingCart;

public interface ShoppingCartService {
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer);
    public ShoppingCart updateItemInCart(Product product,int quantity,Customer customer);
    public ShoppingCart deleteItemFromCart(Product product,Customer customer);
}
