package com.nttdata.order.controller;

import com.nttdata.order.model.Order;
import com.nttdata.order.model.OrderStatus;
import com.nttdata.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciamento de pedidos
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Lista todos os pedidos
     * GET /orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    /**
     * Busca pedido por ID
     * GET /orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo pedido
     * POST /orders
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order) {
        try {
            Order savedOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualiza pedido
     * PUT /orders/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = orderService.update(id, orderDetails);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deleta pedido
     * DELETE /orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Busca pedidos por status
     * GET /orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Busca pedidos por cliente
     * GET /orders/customer/{email}
     */
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable String email) {
        List<Order> orders = orderService.findByCustomerEmail(email);
        return ResponseEntity.ok(orders);
    }

    /**
     * Busca pedidos por produto
     * GET /orders/product/{productId}
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getOrdersByProduct(@PathVariable Long productId) {
        List<Order> orders = orderService.findByProductId(productId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Cancela pedido
     * PUT /orders/{id}/cancel
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        try {
            Order cancelledOrder = orderService.cancelOrder(id);
            return ResponseEntity.ok(cancelledOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Processa pedido
     * PUT /orders/{id}/process
     */
    @PutMapping("/{id}/process")
    public ResponseEntity<?> processOrder(@PathVariable Long id) {
        try {
            Order processedOrder = orderService.processOrder(id);
            return ResponseEntity.ok(processedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Marca pedido como enviado
     * PUT /orders/{id}/ship
     */
    @PutMapping("/{id}/ship")
    public ResponseEntity<?> shipOrder(@PathVariable Long id) {
        try {
            Order shippedOrder = orderService.shipOrder(id);
            return ResponseEntity.ok(shippedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Marca pedido como entregue
     * PUT /orders/{id}/deliver
     */
    @PutMapping("/{id}/deliver")
    public ResponseEntity<?> deliverOrder(@PathVariable Long id) {
        try {
            Order deliveredOrder = orderService.deliverOrder(id);
            return ResponseEntity.ok(deliveredOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}