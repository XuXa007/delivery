package com.example.demo.controller;

import com.example.demo.Enum.ProductType;
import com.example.demo.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с продуктами
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Получить все продукты
     * @return список DTO продуктов
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Получить продукты по типу
     * @param productType тип продукта
     * @return список DTO продуктов
     */
    @GetMapping("/by-type")
    public ResponseEntity<List<ProductDTO>> getProductsByType(@RequestParam ProductType productType) {
        List<ProductDTO> products = productService.getProductsByType(productType);
        return ResponseEntity.ok(products);
    }

    /**
     * Получить продукт по ID
     * @param id ID продукта
     * @return DTO продукта
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Создать новый продукт
     * @param productDTO DTO продукта
     * @return созданный DTO продукта
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Обновить продукт
     * @param id ID продукта
     * @param productDTO DTO продукта
     * @return обновленный DTO продукта
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Удалить продукт
     * @param id ID продукта
     * @return статус выполнения операции
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}