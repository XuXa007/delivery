package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для работы с прайс-листами
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListDTO {

    private Long id;

    @NotNull(message = "ID поставщика должен быть указан")
    private Long providerId;

    @NotNull(message = "ID продукта должен быть указан")
    private Long productId;

    @NotNull(message = "Цена должна быть указана")
    @Positive(message = "Цена должна быть положительной")
    private BigDecimal price;

    @NotNull(message = "Дата начала должна быть указана")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания должна быть указана")
    private LocalDate endDate;

    private Boolean isActive = true;

    // Дополнительные поля для отображения информации в API
    private String providerName;
    private String productName;


}