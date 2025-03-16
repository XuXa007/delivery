package com.example.demo.repository;

import com.example.demo.entity.Delivery;
import com.example.demo.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с поставками
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    /**
     * Найти поставки по поставщику
     * @param provider поставщик
     * @return список поставок
     */
    List<Delivery> findByProvider(Provider provider);

    /**
     * Найти поставки за период
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список поставок
     */
    @Query("SELECT d FROM Delivery d WHERE d.deliveryDate BETWEEN :startDate AND :endDate")
    List<Delivery> findByPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Найти поставки по поставщику за период
     * @param provider поставщик
     * @param startDate начало периода
     * @param endDate конец периода
     * @return список поставок
     */
    @Query("SELECT d FROM Delivery d WHERE d.provider = :provider AND d.deliveryDate BETWEEN :startDate AND :endDate")
    List<Delivery> findByProviderAndPeriod(
            @Param("provider") Provider provider,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
