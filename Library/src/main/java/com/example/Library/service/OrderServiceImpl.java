package com.example.Library.service;

import com.example.Library.entity.CartItem;
import com.example.Library.entity.Order;
import com.example.Library.entity.OrderDetail;
import com.example.Library.entity.ShoppingCart;
import com.example.Library.repository.CartItemRepository;
import com.example.Library.repository.OrderDetailRepository;
import com.example.Library.repository.OrderRepository;
import com.example.Library.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public void saveOder(ShoppingCart cart) {
        Order order=new Order();
        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderDetail>orderDetailList=new ArrayList<>();
        for (CartItem item : cart.getCartItem())
        {
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setProduct(item.getProduct());
            orderDetail.setUnitPrice(item.getProduct().getCostPrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartItemRepository.delete(item);
        }
        order.setOrderDetailList(orderDetailList);
        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrice(0);
        shoppingCartRepository.save(cart);
        orderRepository.save(order);

    }

    @Override
    public void acceptOrder(Long id) {
        Order order=orderRepository.getById(id);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING");
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);

    }
}
