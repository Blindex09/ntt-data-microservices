package com.nttdata.order.repository;

import com.nttdata.order.model.Order;
import com.nttdata.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para operações de banco de dados com Order
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Busca pedidos por status
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Busca pedidos por cliente (email)
     */
    List<Order> findByCustomerEmail(String customerEmail);

    /**
     * Busca pedidos por nome do cliente
     */
    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);

    /**
     * Busca pedidos por produto
     */
    List<Order> findByProductId(Long productId);

    /**
     * Busca pedidos criados em um período
     */
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Busca pedidos por status e período
     */
    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByStatusAndPeriod(@Param("status") OrderStatus status, 
                                     @Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Conta pedidos por status
     */
    long countByStatus(OrderStatus status);

    /**
     * Busca pedidos pendentes mais antigos
     */
    List<Order> findByStatusOrderByCreatedAtAsc(OrderStatus status);
}