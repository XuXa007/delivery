package com.example.demo.service;

import com.example.demo.dto.ProviderDTO;
import com.example.demo.entity.Provider;
import com.example.demo.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с поставщиками
 */
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    /**
     * Получить всех поставщиков
     * @return список DTO поставщиков
     */
    @Transactional(readOnly = true)
    public List<ProviderDTO> getAllProviders() {
        return providerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить поставщика по ID
     * @param id ID поставщика
     * @return DTO поставщика
     */
    @Transactional(readOnly = true)
    public ProviderDTO getProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставщик с ID " + id + " не найден"));
        return mapToDTO(provider);
    }

    /**
     * Создать нового поставщика
     * @param providerDTO DTO поставщика
     * @return созданный DTO поставщика
     */
    @Transactional
    public ProviderDTO createProvider(ProviderDTO providerDTO) {
        Provider provider = mapToEntity(providerDTO);
        Provider savedProvider = providerRepository.save(provider);
        return mapToDTO(savedProvider);
    }

    /**
     * Обновить поставщика
     * @param id ID поставщика
     * @param providerDTO DTO поставщика
     * @return обновленный DTO поставщика
     */
    @Transactional
    public ProviderDTO updateProvider(Long id, ProviderDTO providerDTO) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставщик с ID " + id + " не найден"));

        provider.setName(providerDTO.getName());
        provider.setContactInfo(providerDTO.getContactInfo());

        Provider updatedProvider = providerRepository.save(provider);
        return mapToDTO(updatedProvider);
    }

    /**
     * Удалить поставщика
     * @param id ID поставщика
     */
    @Transactional
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new EntityNotFoundException("Поставщик с ID " + id + " не найден");
        }
        providerRepository.deleteById(id);
    }

    /**
     * Преобразовать Entity в DTO
     * @param provider Entity поставщика
     * @return DTO поставщика
     */
    private ProviderDTO mapToDTO(Provider provider) {
        return ProviderDTO.builder()
                .id(provider.getId())
                .name(provider.getName())
                .contactInfo(provider.getContactInfo())
                .build();
    }

    /**
     * Преобразовать DTO в Entity
     * @param providerDTO DTO поставщика
     * @return Entity поставщика
     */
    private Provider mapToEntity(ProviderDTO providerDTO) {
        return Provider.builder()
                .id(providerDTO.getId())
                .name(providerDTO.getName())
                .contactInfo(providerDTO.getContactInfo())
                .build();
    }

    /**
     * Получить Entity поставщика по ID
     * @param id ID поставщика
     * @return Entity поставщика
     */
    @Transactional(readOnly = true)
    public Provider getProviderEntityById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставщик с ID " + id + " не найден"));
    }
}
