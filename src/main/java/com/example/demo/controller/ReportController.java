package com.example.demo.controller;

import com.example.demo.dto.ReportDTO;
import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Контроллер для работы с отчетами
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * Сформировать отчет о поставках за период
     * @param startDate начало периода
     * @param endDate конец периода
     * @return DTO отчета
     */
    @GetMapping("/deliveries")
    public ResponseEntity<ReportDTO> generateDeliveryReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        ReportDTO report = reportService.generateDeliveryReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
