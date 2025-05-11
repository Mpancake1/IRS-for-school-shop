package com.example.demo.service;

import com.example.demo.NotEnoughStockException;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void createOrder(Order order) {
        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        if (product.getQuantity() < order.getQuantity()) {
            throw new NotEnoughStockException("Недостаточно товара на складе: " + product.getName());
        }

        product.setQuantity(product.getQuantity() - order.getQuantity());
        productRepository.save(product);

        orderRepository.save(order);
    }
}