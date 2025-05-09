package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.RegisteredUser;
import com.example.demo.service.ProductService;
import com.example.demo.service.RegisteredUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RegisteredUserService userService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(
            RegisteredUserService userService,
            ProductService productService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.productService = productService;
        this.passwordEncoder = passwordEncoder;
    }

    // Главная страница админ-панели
    @GetMapping("/panel")
    public String adminPanel(Model model) {
        return "admin-panel";
    }

    // Управление товарами
    @GetMapping("/panel-products")
    public String adminPanelProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin-panel-products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/admin/panel-products";
    }

    @GetMapping("/edit-product/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/edit-product/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return "redirect:/admin/panel-products";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/panel-products";
    }

    // Управление пользователями
    @GetMapping("/panel-users")
    public String adminPanelUsers(Model model) {
        List<RegisteredUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-panel-users";
    }

    @GetMapping("/add-user")
    public String addUserForm() {
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.addUser(username, passwordEncoder.encode(password), role);
        return "redirect:/admin/panel-users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        RegisteredUser user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/edit-user/{id}")
    public String editUser(@PathVariable Long id, @RequestParam String username, @RequestParam String role) {
        userService.updateUser(id, username, role);
        return "redirect:/admin/panel-users";
    }

    @GetMapping("/toggle-user/{id}")
    public String toggleUserStatus(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return "redirect:/admin/panel-users";
    }
}