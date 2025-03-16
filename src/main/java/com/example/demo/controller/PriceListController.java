package com.example.demo.controller;

import com.example.demo.dto.PriceListDTO;
import com.example.demo.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для работы с прайс-листами
 */
@RestController
@RequestMapping("/api/price-lists")
@RequiredArgsConstructor
public class PriceListController {

    private final PriceListService priceListService;

    /**
     * Получить все прайс-листы
     * @return список DTO прайс-листов
     */
    @GetMapping
    public ResponseEntity<List<PriceListDTO>> getAllPriceLists() {
        List<PriceListDTO> priceLists = priceListService.getAllPriceLists();
        return ResponseEntity.ok(priceLists);
    }

    /**
     * Получить прайс-листы по поставщику
     * @param providerId ID поставщика
     * @return список DTO прайс-листов
     */
    @GetMapping("/by-provider/{providerId}")
    public ResponseEntity<List<PriceListDTO>> getPriceListsByProvider(@PathVariable Long providerId) {
        List<PriceListDTO> priceLists = priceListService.getPriceListsByProvider(providerId);
        return ResponseEntity.ok(priceLists);
    }

    /**
     * Получить прайс-лист по ID
     * @param id ID прайс-листа
     * @return DTO прайс-листа
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceListDTO> getPriceListById(@PathVariable Long id) {
        PriceListDTO priceList = priceListService.getPriceListById(id);
        return ResponseEntity.ok(priceList);
    }

    /**
     * Получить актуальный прайс-лист для поставщика и продукта
     * @param providerId ID поставщика
     * @param productId ID продукта
     * @param date дата
     * @return DTO прайс-листа
     */
    @GetMapping("/active")
    public ResponseEntity<PriceListDTO> getActivePriceList(
            @RequestParam Long providerId,
            @RequestParam Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        PriceListDTO priceList = priceListService.getActivePriceList(providerId, productId, date);
        return ResponseEntity.ok(priceList);
    }

    /**
     * Создать новый прайс-лист
     * @param priceListDTO DTO прайс-листа
     * @return созданный DTO прайс-листа
     */
    @PostMapping
    public ResponseEntity<PriceListDTO> createPriceList(@Valid @RequestBody PriceListDTO priceListDTO) {
        PriceListDTO createdPriceList = priceListService.createPriceList(priceListDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPriceList);
    }

    /**
     * Обновить прайс-лист
     * @param id ID прайс-листа
     * @param priceListDTO DTO прайс-листа
     * @return обновленный DTO прайс-листа
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceListDTO> updatePriceList(@PathVariable Long id, @Valid @RequestBody PriceListDTO priceListDTO) {
        PriceListDTO updatedPriceList = priceListService.updatePriceList(id, priceListDTO);
        return ResponseEntity.ok(updatedPriceList);
    }

    /**
     * Деактивировать прайс-лист
     * @param id ID прайс-листа
     * @return обновленный DTO прайс-листа
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<PriceListDTO> deactivatePriceList(@PathVariable Long id) {
        PriceListDTO updatedPriceList = priceListService.deactivatePriceList(id);
        return ResponseEntity.ok(updatedPriceList);
    }

    /**
     * Удалить прайс-лист
     * @param id ID прайс-листа
     * @return статус выполнения операции
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceList(@PathVariable Long id) {
        priceListService.deletePriceList(id);
        return ResponseEntity.noContent().build();
    }
}
