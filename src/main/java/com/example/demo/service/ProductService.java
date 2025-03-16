package com.example.demo.service;

import com.example.demo.Enum.ProductType;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с продуктами
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Получить все продукты
     * @return список DTO продуктов
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить продукты по типу
     * @param productType тип продукта
     * @return список DTO продуктов
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByType(ProductType productType) {
        return productRepository.findByProductType(productType).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить продукт по ID
     * @param id ID продукта
     * @return DTO продукта
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с ID " + id + " не найден"));
        return mapToDTO(product);
    }

    /**
     * Создать новый продукт
     * @param productDTO DTO продукта
     * @return созданный DTO продукта
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    /**
     * Обновить продукт
     * @param id ID продукта
     * @param productDTO DTO продукта
     * @return обновленный DTO продукта
     */
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с ID " + id + " не найден"));

        product.setName(productDTO.getName());
        product.setProductType(productDTO.getProductType());
        product.setDescription(productDTO.getDescription());

        Product updatedProduct = productRepository.save(product);
        return mapToDTO(updatedProduct);
    }

    /**
     * Удалить продукт
     * @param id ID продукта
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Продукт с ID " + id + " не найден");
        }
        productRepository.deleteById(id);
    }

    /**
     * Преобразовать Entity в DTO
     * @param product Entity продукта
     * @return DTO продукта
     */
    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .productType(product.getProductType())
                .description(product.getDescription())
                .build();
    }

    /**
     * Преобразовать DTO в Entity
     * @param productDTO DTO продукта
     * @return Entity продукта
     */
    private Product mapToEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .productType(productDTO.getProductType())
                .description(productDTO.getDescription())
                .build();
    }

    /**
     * Получить Entity продукта по ID
     * @param id ID продукта
     * @return Entity продукта
     */
    @Transactional(readOnly = true)
    public Product getProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с ID " + id + " не найден"));
    }
}
