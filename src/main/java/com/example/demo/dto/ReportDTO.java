package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO для отчета о поставках
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProviderReportDTO {
        private Long providerId;
        private String providerName;
        private List<ProductReportDTO> products = new ArrayList<>();
        private BigDecimal totalWeight = BigDecimal.ZERO;
        private BigDecimal totalCost = BigDecimal.ZERO;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReportDTO {
        private Long productId;
        private String productName;
        private String productType;
        private BigDecimal totalWeight = BigDecimal.ZERO;
        private BigDecimal totalCost = BigDecimal.ZERO;
    }

    private List<ProviderReportDTO> providerReports = new ArrayList<>();
    private BigDecimal totalWeight = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;
}
