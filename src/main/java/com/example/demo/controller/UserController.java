package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class UserController {

    private final ProductService productService;

    @Autowired
    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String viewProducts(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "sort", required = false) String sort) {
        List<Product> products;

        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchProducts(keyword);
        } else if (sort != null) {
            switch (sort) {
                case "priceAsc":
                    products = productService.sortProductsByPriceAsc();
                    break;
                case "priceDesc":
                    products = productService.sortProductsByPriceDesc();
                    break;
                case "nameAsc":
                    products = productService.sortProductsByNameAsc();
                    break;
                case "nameDesc":
                    products = productService.sortProductsByNameDesc();
                    break;
                default:
                    products = productService.getAllProducts();
                    break;
            }
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        return "products"; // имя твоего шаблона
    }
}