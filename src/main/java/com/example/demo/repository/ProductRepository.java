package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Поиск товаров по части имени или артикулу
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.article) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    // Сортировка по цене (по возрастанию)
    List<Product> findAllByOrderByPriceAsc();

    // Сортировка по цене (по убыванию)
    List<Product> findAllByOrderByPriceDesc();

    // Сортировка по имени (по алфавиту, по возрастанию)
    List<Product> findAllByOrderByNameAsc();

    // Сортировка по имени (по убыванию)
    List<Product> findAllByOrderByNameDesc();
}