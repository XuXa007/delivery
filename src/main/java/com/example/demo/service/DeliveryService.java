package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с поставками
 */
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final ProviderService providerService;
    private final ProductService productService;

    /**
     * Получить все поставки
     * @return список DTO поставок
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить поставки за период
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список DTO поставок
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> getDeliveriesByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return deliveryRepository.findByPeriod(startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить поставки по поставщику
     * @param providerId ID поставщика
     * @return список DTO поставок
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> getDeliveriesByProvider(Long providerId) {
        Provider provider = providerService.getProviderEntityById(providerId);
        return deliveryRepository.findByProvider(provider).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить поставки по поставщику за период
     * @param providerId ID поставщика
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список DTO поставок
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> getDeliveriesByProviderAndPeriod(Long providerId, LocalDateTime startDate, LocalDateTime endDate) {
        Provider provider = providerService.getProviderEntityById(providerId);
        return deliveryRepository.findByProviderAndPeriod(provider, startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить поставку по ID
     * @param id ID поставки
     * @return DTO поставки
     */
    @Transactional(readOnly = true)
    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставка с ID " + id + " не найдена"));
        return mapToDTO(delivery);
    }

    /**
     * Создать новую поставку
     * @param deliveryDTO DTO поставки
     * @return созданный DTO поставки
     */
    @Transactional
    public DeliveryDTO createDelivery(DeliveryDTO  deliveryDTO) {
        Provider provider = providerService.getProviderEntityById(deliveryDTO.getProviderId());

        Delivery delivery = Delivery.builder()
                .provider(provider)
                .deliveryDate(deliveryDTO.getDeliveryDate())
                .comment(deliveryDTO.getComment())
                .build();

        Delivery savedDelivery = deliveryRepository.save(delivery);

        // Добавляем позиции поставки
        deliveryDTO.getItems().forEach(itemDTO -> {
            Product product = productService.getProductEntityById(itemDTO.getProductId());

            DeliveryItem item = DeliveryItem.builder()
                    .delivery(savedDelivery)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .build();

            savedDelivery.addItem(item);
        });

        Delivery resultDelivery = deliveryRepository.save(savedDelivery);
        return mapToDTO(resultDelivery);
    }

    /**
     * Обновить поставку
     * @param id ID поставки
     * @param deliveryDTO DTO поставки
     * @return обновленный DTO поставки
     */
    @Transactional
    public DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставка с ID " + id + " не найдена"));

        Provider provider = providerService.getProviderEntityById(deliveryDTO.getProviderId());

        delivery.setProvider(provider);
        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate());
        delivery.setComment(deliveryDTO.getComment());

        // Очищаем старые позиции
        delivery.getItems().clear();

        // Добавляем новые позиции
        deliveryDTO.getItems().forEach(itemDTO -> {
            Product product = productService.getProductEntityById(itemDTO.getProductId());

            DeliveryItem item = DeliveryItem.builder()
                    .delivery(delivery)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .build();

            delivery.addItem(item);
        });

        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return mapToDTO(updatedDelivery);
    }

    /**
     * Удалить поставку
     * @param id ID поставки
     */
    @Transactional
    public void deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) {
            throw new EntityNotFoundException("Поставка с ID " + id + " не найдена");
        }
        deliveryRepository.deleteById(id);
    }

    /**
     * Преобразовать Entity в DTO
     * @param delivery Entity поставки
     * @return DTO поставки
     */
    private DeliveryDTO mapToDTO(Delivery delivery) {
        DeliveryDTO dto = DeliveryDTO.builder()
                .id(delivery.getId())
                .providerId(delivery.getProvider().getId())
                .providerName(delivery.getProvider().getName())
                .deliveryDate(delivery.getDeliveryDate())
                .comment(delivery.getComment())
                .build();

        List<DeliveryItemDTO> itemDTOs = delivery.getItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }

    /**
     * Преобразовать Entity позиции поставки в DTO
     * @param item Entity позиции поставки
     * @return DTO позиции поставки
     */
    private DeliveryItemDTO mapItemToDTO(DeliveryItem item) {
        return DeliveryItemDTO.builder()
                .id(item.getId())
                .deliveryId(item.getDelivery().getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}