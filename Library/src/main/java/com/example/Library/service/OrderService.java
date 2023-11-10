package com.example.Library.service;

import com.example.Library.entity.ShoppingCart;

public interface OrderService {
    public void saveOder(ShoppingCart cart);
    public void acceptOrder(Long id);
    public void cancelOrder(Long id);
}
