package com.nttdata.product.repository;

import com.nttdata.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de banco de dados com Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca produtos por nome (case insensitive)
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Busca produtos com estoque maior que zero
     */
    List<Product> findByStockGreaterThan(Integer stock);

    /**
     * Busca produtos por faixa de preço
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Busca produtos com estoque baixo (menor que determinado valor)
     */
    @Query("SELECT p FROM Product p WHERE p.stock < :minStock")
    List<Product> findLowStockProducts(@Param("minStock") Integer minStock);

    /**
     * Verifica se produto existe por nome
     */
    boolean existsByName(String name);

    /**
     * Busca produto por nome exato
     */
    Optional<Product> findByName(String name);
}