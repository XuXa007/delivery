package com.example.demo.repository;

import com.example.demo.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с поставщиками
 */
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    /**
     * Найти поставщика по имени
     * @param name имя поставщика
     * @return поставщик
     */
    Optional<Provider> findByName(String name);
}
