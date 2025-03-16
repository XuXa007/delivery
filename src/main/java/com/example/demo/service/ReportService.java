package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для формирования отчетов
 */
@Service
@RequiredArgsConstructor
public class ReportService {

    private final DeliveryRepository deliveryRepository;

    /**
     * Сформировать отчет о поставках за период
     * @param startDate начало периода
     * @param endDate конец периода
     * @return DTO отчета
     */
    @Transactional(readOnly = true)
    public ReportDTO generateDeliveryReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Delivery> deliveries = deliveryRepository.findByPeriod(startDate, endDate);

        // Группировка данных по поставщикам
        Map<Provider, List<DeliveryItem>> itemsByProvider = new HashMap<>();

        for (Delivery delivery : deliveries) {
            Provider provider = delivery.getProvider();

            if (!itemsByProvider.containsKey(provider)) {
                itemsByProvider.put(provider, new ArrayList<>());
            }

            itemsByProvider.get(provider).addAll(delivery.getItems());
        }

        // Формирование отчета
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setStartDate(startDate);
        reportDTO.setEndDate(endDate);

        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        List<ReportDTO.ProviderReportDTO> providerReports = new ArrayList<>();

        for (Map.Entry<Provider, List<DeliveryItem>> entry : itemsByProvider.entrySet()) {
            Provider provider = entry.getKey();
            List<DeliveryItem> items = entry.getValue();

            ReportDTO.ProviderReportDTO providerReport = new ReportDTO.ProviderReportDTO();
            providerReport.setProviderId(provider.getId());
            providerReport.setProviderName(provider.getName());

            // Группировка данных по продуктам
            Map<Long, ReportDTO.ProductReportDTO> productReports = new HashMap<>();

            for (DeliveryItem item : items) {
                Long productId = item.getProduct().getId();
                BigDecimal quantity = item.getQuantity();
                BigDecimal cost = item.getTotalPrice();

                if (!productReports.containsKey(productId)) {
                    ReportDTO.ProductReportDTO productReport = new ReportDTO.ProductReportDTO();
                    productReport.setProductId(productId);
                    productReport.setProductName(item.getProduct().getName());
                    productReport.setProductType(item.getProduct().getProductType().getDisplayName());
                    productReport.setTotalWeight(BigDecimal.ZERO);
                    productReport.setTotalCost(BigDecimal.ZERO);

                    productReports.put(productId, productReport);
                }

                ReportDTO.ProductReportDTO productReport = productReports.get(productId);
                productReport.setTotalWeight(productReport.getTotalWeight().add(quantity));
                productReport.setTotalCost(productReport.getTotalCost().add(cost));
            }

            List<ReportDTO.ProductReportDTO> productReportList = new ArrayList<>(productReports.values());
            providerReport.setProducts(productReportList);

            // Расчет итогов по поставщику
            BigDecimal providerTotalWeight = productReportList.stream()
                    .map(ReportDTO.ProductReportDTO::getTotalWeight)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal providerTotalCost = productReportList.stream()
                    .map(ReportDTO.ProductReportDTO::getTotalCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            providerReport.setTotalWeight(providerTotalWeight);
            providerReport.setTotalCost(providerTotalCost);

            providerReports.add(providerReport);

            // Добавление к общим итогам
            totalWeight = totalWeight.add(providerTotalWeight);
            totalCost = totalCost.add(providerTotalCost);
        }

        reportDTO.setProviderReports(providerReports);
        reportDTO.setTotalWeight(totalWeight);
        reportDTO.setTotalCost(totalCost);

        return reportDTO;
    }
}
