package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * DTO для работы с поставщиками
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDTO {

    private Long id;

    @NotBlank(message = "Имя поставщика не может быть пустым")
    private String name;

    private String contactInfo;
}