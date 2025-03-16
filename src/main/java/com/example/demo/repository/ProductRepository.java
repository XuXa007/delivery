package com.example.demo.repository;

import com.example.demo.Enum.ProductType;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с продуктами
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Найти продукт по имени
     * @param name имя продукта
     * @return продукт
     */
    Optional<Product> findByName(String name);

    /**
     * Найти продукты по типу
     * @param productType тип продукта
     * @return список продуктов
     */
    List<Product> findByProductType(ProductType productType);
}