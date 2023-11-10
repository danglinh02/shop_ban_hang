package com.example.Library.service;

import com.example.Library.entity.CartItem;
import com.example.Library.entity.Customer;
import com.example.Library.entity.Product;
import com.example.Library.entity.ShoppingCart;
import com.example.Library.repository.CartItemRepository;
import com.example.Library.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
       ShoppingCart cart=customer.getShoppingCart();
       if (cart == null)
       {
           cart=new ShoppingCart();
       }
        Set<CartItem>cartItems=cart.getCartItem();
        CartItem cartItem =findCartItems(cartItems,product.getId());
       if(cartItems == null)
       {
           cartItems=new HashSet<>();
           if (cartItem == null)
           {
               cartItem=new CartItem();
               cartItem.setProduct(product);
               cartItem.setTotalPrice(quantity * product.getCostPrice());
               cartItem.setQuantity(quantity);
               cartItem.setCart(cart);
               cartItems.add(cartItem);
               cartItemRepository.save(cartItem);
           }

       }
       else {
            if(cartItem == null)
            {
                cartItems=new HashSet<>();
                if (cartItem == null)
                {
                    cartItem=new CartItem();
                    cartItem.setProduct(product);
                    cartItem.setTotalPrice(quantity * product.getCostPrice());
                    cartItem.setQuantity(quantity);
                    cartItem.setCart(cart);
                    cartItems.add(cartItem);
                    cartItemRepository.save(cartItem);
                }
            }
            else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice()+(quantity * product.getCostPrice()));
                cartItemRepository.save(cartItem);
            }
       }
        cart.setCartItem(cartItems);
        int totalItems=totalItems(cart.getCartItem());
        double totalPrice=totalPrice(cart.getCartItem());
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setCustomer(customer);
       return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemInCart(Product product, int quantity, Customer customer) {
       ShoppingCart cart=customer.getShoppingCart();
       Set<CartItem>cartItems=cart.getCartItem();
       CartItem item= findCartItems(cartItems, product.getId());
       item.setQuantity(quantity);
       item.setTotalPrice(quantity * product.getCostPrice());
       cartItemRepository.save(item);
       int totalItems=totalItems(cartItems);
       double totalPrice=totalPrice(cartItems);
       cart.setTotalItems(totalItems);
       cart.setTotalPrice(totalPrice);
       return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart deleteItemFromCart(Product product, Customer customer) {
        ShoppingCart cart =customer.getShoppingCart();
        Set<CartItem>cartItems=cart.getCartItem();
        CartItem item=findCartItems(cartItems,product.getId());
        cartItems.remove(item);
        cartItemRepository.delete(item);
        double totalPrice=totalPrice(cartItems);
        int totalItems=totalItems(cartItems);
        cart.setCartItem(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        return this.shoppingCartRepository.save(cart);
    }

    private CartItem findCartItems(Set<CartItem>cartItems,Long productId)
    {
        if(cartItems == null)
        {
            return null;
        }
        CartItem cartItem=null;
        for (CartItem item:cartItems)
        {
            if (item.getProduct().getId()== productId)
            {
                cartItem=item;
            }

        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems)
    {
        int totalItems=0;
        for (CartItem item:cartItems)
        {
            totalItems+=item.getQuantity();
        }
        return totalItems;
    }
    private double totalPrice(Set<CartItem>cartItems)
    {
        double totalPrice=0.0;
        for(CartItem item :cartItems)
        {
            totalPrice+=item.getTotalPrice();
        }
        return totalPrice;
    }






}
