package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Получить все товары
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Поиск товаров
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

    // Сортировка по цене (по возрастанию)
    public List<Product> sortProductsByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    // Сортировка по цене (по убыванию)
    public List<Product> sortProductsByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    // Сортировка по имени (по алфавиту, по возрастанию)
    public List<Product> sortProductsByNameAsc() {
        return productRepository.findAllByOrderByNameAsc();
    }

    // Сортировка по имени (по убыванию)
    public List<Product> sortProductsByNameDesc() {
        return productRepository.findAllByOrderByNameDesc();
    }

    // Добавить новый товар
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    // Получить товар по ID
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    // Обновить товар
    public void updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setArticle(updatedProduct.getArticle());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            product.setQuantity(updatedProduct.getQuantity());
            productRepository.save(product);
        }
    }

    // Удалить товар
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}