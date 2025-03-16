package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO для работы с позициями поставки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryItemDTO {

    private Long id;

    private Long deliveryId;

    @NotNull(message = "ID продукта должен быть указан")
    private Long productId;

    @NotNull(message = "Количество должно быть указано")
    @Positive(message = "Количество должно быть положительным")
    private BigDecimal quantity;

    @NotNull(message = "Цена должна быть указана")
    @Positive(message = "Цена должна быть положительной")
    private BigDecimal price;

    // Дополнительные поля для отображения информации в API
    private String productName;
    private BigDecimal totalPrice;
}