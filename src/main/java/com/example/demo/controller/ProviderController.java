package com.example.demo.controller;

import com.example.demo.dto.ProviderDTO;
import com.example.demo.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с поставщиками
 */
@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    /**
     * Получить всех поставщиков
     * @return список DTO поставщиков
     */
    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAllProviders() {
        List<ProviderDTO> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    /**
     * Получить поставщика по ID
     * @param id ID поставщика
     * @return DTO поставщика
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getProviderById(@PathVariable Long id) {
        ProviderDTO provider = providerService.getProviderById(id);
        return ResponseEntity.ok(provider);
    }

    /**
     * Создать нового поставщика
     * @param providerDTO DTO поставщика
     * @return созданный DTO поставщика
     */
    @PostMapping
    public ResponseEntity<ProviderDTO> createProvider(@Valid @RequestBody ProviderDTO providerDTO) {
        ProviderDTO createdProvider = providerService.createProvider(providerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProvider);
    }

    /**
     * Обновить поставщика
     * @param id ID поставщика
     * @param providerDTO DTO поставщика
     * @return обновленный DTO поставщика
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProvider(@PathVariable Long id, @Valid @RequestBody ProviderDTO providerDTO) {
        ProviderDTO updatedProvider = providerService.updateProvider(id, providerDTO);
        return ResponseEntity.ok(updatedProvider);
    }

    /**
     * Удалить поставщика
     * @param id ID поставщика
     * @return статус выполнения операции
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
