package com.example.demo.dto;


import com.example.demo.Enum.ProductType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO для работы с продуктами
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Имя продукта не может быть пустым")
    private String name;

    @NotNull(message = "Тип продукта должен быть указан")
    private ProductType productType;

    private String description;

}