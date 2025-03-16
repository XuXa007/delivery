package com.example.demo.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO для работы с поставками
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

    private Long id;

    @NotNull(message = "ID поставщика должен быть указан")
    private Long providerId;

    @NotNull(message = "Дата поставки должна быть указана")
    private LocalDateTime deliveryDate;

    private String comment;

    // Дополнительное поле для отображения информации в API
    private String providerName;

    @NotEmpty(message = "Поставка должна содержать хотя бы одну позицию")
    @Valid
    private List<DeliveryItemDTO> items = new ArrayList<>();
}
