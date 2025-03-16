package com.example.demo.controller;

import com.example.demo.dto.DeliveryDTO;
import com.example.demo.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для работы с поставками
 */
@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * Получить все поставки
     * @return список DTO поставок
     */
    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    /**
     * Получить поставки за период
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список DTO поставок
     */
    @GetMapping("/by-period")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<DeliveryDTO> deliveries = deliveryService.getDeliveriesByPeriod(startDate, endDate);
        return ResponseEntity.ok(deliveries);
    }

    /**
     * Получить поставки по поставщику
     * @param providerId ID поставщика
     * @return список DTO поставок
     */
    @GetMapping("/by-provider/{providerId}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByProvider(@PathVariable Long providerId) {
        List<DeliveryDTO> deliveries = deliveryService.getDeliveriesByProvider(providerId);
        return ResponseEntity.ok(deliveries);
    }

    /**
     * Получить поставки по поставщику за период
     * @param providerId ID поставщика
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список DTO поставок
     */
    @GetMapping("/by-provider/{providerId}/by-period")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByProviderAndPeriod(
            @PathVariable Long providerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<DeliveryDTO> deliveries = deliveryService.getDeliveriesByProviderAndPeriod(providerId, startDate, endDate);
        return ResponseEntity.ok(deliveries);
    }

    /**
     * Получить поставку по ID
     * @param id ID поставки
     * @return DTO поставки
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        DeliveryDTO delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

    /**
     * Создать новую поставку
     * @param deliveryDTO DTO поставки
     * @return созданный DTO поставки
     */
    @PostMapping
    public ResponseEntity<DeliveryDTO> createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {
        DeliveryDTO createdDelivery = deliveryService.createDelivery(deliveryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDelivery);
    }

    /**
     * Обновить поставку
     * @param id ID поставки
     * @param deliveryDTO DTO поставки
     * @return обновленный DTO поставки
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryDTO deliveryDTO) {
        DeliveryDTO updatedDelivery = deliveryService.updateDelivery(id, deliveryDTO);
        return ResponseEntity.ok(updatedDelivery);
    }

    /**
     * Удалить поставку
     * @param id ID поставки
     * @return статус выполнения операции
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
