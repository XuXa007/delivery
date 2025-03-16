package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с прайс-листами
 */
@Service
@RequiredArgsConstructor
public class PriceListService {

    private final PriceListRepository priceListRepository;
    private final ProviderService providerService;
    private final ProductService productService;

    /**
     * Получить все прайс-листы
     * @return список DTO прайс-листов
     */
    @Transactional(readOnly = true)
    public List<PriceListDTO> getAllPriceLists() {
        return priceListRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить прайс-листы по поставщику
     * @param providerId ID поставщика
     * @return список DTO прайс-листов
     */
    @Transactional(readOnly = true)
    public List<PriceListDTO> getPriceListsByProvider(Long providerId) {
        Provider provider = providerService.getProviderEntityById(providerId);
        return priceListRepository.findByProviderAndIsActiveTrue(provider).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить прайс-лист по ID
     * @param id ID прайс-листа
     * @return DTO прайс-листа
     */
    @Transactional(readOnly = true)
    public PriceListDTO getPriceListById(Long id) {
        PriceList priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Прайс-лист с ID " + id + " не найден"));
        return mapToDTO(priceList);
    }

    /**
     * Получить актуальный прайс-лист для поставщика и продукта
     * @param providerId ID поставщика
     * @param productId ID продукта
     * @param date дата
     * @return DTO прайс-листа
     */
    @Transactional(readOnly = true)
    public PriceListDTO getActivePriceList(Long providerId, Long productId, LocalDate date) {
        Provider provider = providerService.getProviderEntityById(providerId);
        Product product = productService.getProductEntityById(productId);

        PriceList priceList = priceListRepository.findActiveByProviderAndProductAndDate(provider, product, date)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Актуальный прайс-лист для поставщика с ID " + providerId +
                                " и продукта с ID " + productId + " на дату " + date + " не найден"));

        return mapToDTO(priceList);
    }

    /**
     * Создать новый прайс-лист
     * @param priceListDTO DTO прайс-листа
     * @return созданный DTO прайс-листа
     */
    @Transactional
    public PriceListDTO createPriceList(PriceListDTO priceListDTO) {
        Provider provider = providerService.getProviderEntityById(priceListDTO.getProviderId());
        Product product = productService.getProductEntityById(priceListDTO.getProductId());

        PriceList priceList = PriceList.builder()
                .provider(provider)
                .product(product)
                .price(priceListDTO.getPrice())
                .startDate(priceListDTO.getStartDate())
                .endDate(priceListDTO.getEndDate())
                .isActive(priceListDTO.getIsActive())
                .build();

        PriceList savedPriceList = priceListRepository.save(priceList);
        return mapToDTO(savedPriceList);
    }

    /**
     * Обновить прайс-лист
     * @param id ID прайс-листа
     * @param priceListDTO DTO прайс-листа
     * @return обновленный DTO прайс-листа
     */
    @Transactional
    public PriceListDTO updatePriceList(Long id, PriceListDTO priceListDTO) {
        PriceList priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Прайс-лист с ID " + id + " не найден"));

        Provider provider = providerService.getProviderEntityById(priceListDTO.getProviderId());
        Product product = productService.getProductEntityById(priceListDTO.getProductId());

        priceList.setProvider(provider);
        priceList.setProduct(product);
        priceList.setPrice(priceListDTO.getPrice());
        priceList.setStartDate(priceListDTO.getStartDate());
        priceList.setEndDate(priceListDTO.getEndDate());
        priceList.setIsActive(priceListDTO.getIsActive());

        PriceList updatedPriceList = priceListRepository.save(priceList);
        return mapToDTO(updatedPriceList);
    }

    /**
     * Деактивировать прайс-лист
     * @param id ID прайс-листа
     * @return обновленный DTO прайс-листа
     */
    @Transactional
    public PriceListDTO deactivatePriceList(Long id) {
        PriceList priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Прайс-лист с ID " + id + " не найден"));

        priceList.setIsActive(false);

        PriceList updatedPriceList = priceListRepository.save(priceList);
        return mapToDTO(updatedPriceList);
    }

    /**
     * Удалить прайс-лист
     * @param id ID прайс-листа
     */
    @Transactional
    public void deletePriceList(Long id) {
        if (!priceListRepository.existsById(id)) {
            throw new EntityNotFoundException("Прайс-лист с ID " + id + " не найден");
        }
        priceListRepository.deleteById(id);
    }

    /**
     * Преобразовать Entity в DTO
     * @param priceList Entity прайс-листа
     * @return DTO прайс-листа
     */
    private PriceListDTO mapToDTO(PriceList priceList) {
        return PriceListDTO.builder()
                .id(priceList.getId())
                .providerId(priceList.getProvider().getId())
                .providerName(priceList.getProvider().getName())
                .productId(priceList.getProduct().getId())
                .productName(priceList.getProduct().getName())
                .price(priceList.getPrice())
                .startDate(priceList.getStartDate())
                .endDate(priceList.getEndDate())
                .isActive(priceList.getIsActive())
                .build();
    }
}
