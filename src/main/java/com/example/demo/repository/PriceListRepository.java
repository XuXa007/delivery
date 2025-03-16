package com.example.demo.repository;

import com.example.demo.entity.PriceList;
import com.example.demo.entity.Product;
import com.example.demo.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с прайс-листами
 */
@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    /**
     * Найти актуальный прайс-лист для поставщика и продукта на указанную дату
     * @param provider поставщик
     * @param product продукт
     * @param date дата
     * @return прайс-лист
     */
    @Query("SELECT pl FROM PriceList pl WHERE pl.provider = :provider AND pl.product = :product " +
            "AND pl.startDate <= :date AND pl.endDate >= :date AND pl.isActive = true")
    Optional<PriceList> findActiveByProviderAndProductAndDate(
            @Param("provider") Provider provider,
            @Param("product") Product product,
            @Param("date") LocalDate date);

    /**
     * Найти все актуальные прайс-листы для поставщика
     * @param provider поставщик
     * @return список прайс-листов
     */
    List<PriceList> findByProviderAndIsActiveTrue(Provider provider);

    /**
     * Найти все актуальные прайс-листы для продукта
     * @param product продукт
     * @return список прайс-листов
     */
    List<PriceList> findByProductAndIsActiveTrue(Product product);

    /**
     * Найти все актуальные прайс-листы для поставщика на указанную дату
     * @param provider поставщик
     * @param date дата
     * @return список прайс-листов
     */
    @Query("SELECT pl FROM PriceList pl WHERE pl.provider = :provider " +
            "AND pl.startDate <= :date AND pl.endDate >= :date AND pl.isActive = true")
    List<PriceList> findActiveByProviderAndDate(
            @Param("provider") Provider provider,
            @Param("date") LocalDate date);
}