package com.example.demo.repository;

import com.example.demo.entity.Delivery;
import com.example.demo.entity.DeliveryItem;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с позициями поставок
 */
@Repository
public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long> {

    /**
     * Найти позиции поставки
     * @param delivery поставка
     * @return список позиций поставки
     */
    List<DeliveryItem> findByDelivery(Delivery delivery);

    /**
     * Найти позиции поставок по продукту
     * @param product продукт
     * @return список позиций поставок
     */
    List<DeliveryItem> findByProduct(Product product);

    /**
     * Найти позиции поставок по продукту за период
     * @param product продукт
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список позиций поставок
     */
    @Query("SELECT di FROM DeliveryItem di JOIN di.delivery d " +
            "WHERE di.product = :product AND d.deliveryDate BETWEEN :startDate AND :endDate")
    List<DeliveryItem> findByProductAndDeliveryPeriod(
            @Param("product") Product product,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
