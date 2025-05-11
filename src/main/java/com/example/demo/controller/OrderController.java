package com.example.demo.controller;

import com.example.demo.NotEnoughStockException;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/new")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "orders/create-order";
    }

    @PostMapping("/new")
    public String createOrder(@ModelAttribute Order order, Model model) {
        try {
            orderService.createOrder(order);
            return "redirect:/orders/new";
        } catch (NotEnoughStockException ex) {
            // Возвращаем на ту же страницу с сообщением об ошибке
            model.addAttribute("order", order);
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("orders", orderRepository.findAll());
            model.addAttribute("errorMessage", ex.getMessage());
            return "orders/create-order";
        }
    }
}