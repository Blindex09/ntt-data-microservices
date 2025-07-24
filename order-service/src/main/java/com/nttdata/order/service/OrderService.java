package com.nttdata.order.service;

import com.nttdata.order.client.ProductServiceClient;
import com.nttdata.order.model.Order;
import com.nttdata.order.model.OrderStatus;
import com.nttdata.order.model.ProductDTO;
import com.nttdata.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service para regras de negócio de pedidos
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    /**
     * Lista todos os pedidos
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Busca pedido por ID
     */
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Cria um novo pedido
     */
    public Order createOrder(Order order) {
        try {
            // 1. Verificar se o produto existe
            ProductDTO product = productServiceClient.getProduct(order.getProductId());
            if (product == null) {
                throw new RuntimeException("Produto não encontrado com ID: " + order.getProductId());
            }

            // 2. Verificar se há estoque suficiente
            Boolean hasStock = productServiceClient.checkStock(order.getProductId(), order.getQuantity());
            if (hasStock == null || !hasStock) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
            }

            // 3. Preencher dados do produto no pedido
            order.setProductName(product.getName());
            order.setUnitPrice(product.getPrice());
            order.setTotalPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(order.getQuantity())));
            order.setStatus(OrderStatus.PENDING);

            // 4. Salvar o pedido
            Order savedOrder = orderRepository.save(order);

            // 5. Reduzir estoque do produto
            Boolean stockReduced = productServiceClient.reduceStock(order.getProductId(), order.getQuantity());
            if (stockReduced == null || !stockReduced) {
                // Rollback: deletar o pedido se não conseguiu reduzir o estoque
                orderRepository.delete(savedOrder);
                throw new RuntimeException("Erro ao reduzir estoque do produto");
            }

            // 6. Confirmar o pedido
            savedOrder.setStatus(OrderStatus.CONFIRMED);
            savedOrder.onUpdate();
            return orderRepository.save(savedOrder);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar pedido: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza um pedido
     */
    public Order update(Long id, Order orderDetails) {
        return orderRepository.findById(id)
                .map(order -> {
                    // Só permite atualizar alguns campos para pedidos não finalizados
                    if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
                        throw new RuntimeException("Pedido já finalizado, não pode ser alterado");
                    }
                    
                    order.setCustomerName(orderDetails.getCustomerName());
                    order.setCustomerEmail(orderDetails.getCustomerEmail());
                    order.setStatus(orderDetails.getStatus());
                    order.onUpdate();
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    /**
     * Deleta um pedido (apenas se estiver pendente)
     */
    public void deleteById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser deletados");
        }

        orderRepository.deleteById(id);
    }

    /**
     * Busca pedidos por status
     */
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * Busca pedidos por cliente
     */
    public List<Order> findByCustomerEmail(String customerEmail) {
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    /**
     * Busca pedidos por produto
     */
    public List<Order> findByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }

    /**
     * Cancela um pedido
     */
    public Order cancelOrder(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    if (order.getStatus() == OrderStatus.DELIVERED) {
                        throw new RuntimeException("Pedido já entregue, não pode ser cancelado");
                    }
                    if (order.getStatus() == OrderStatus.CANCELLED) {
                        throw new RuntimeException("Pedido já foi cancelado");
                    }
                    
                    order.setStatus(OrderStatus.CANCELLED);
                    order.onUpdate();
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    /**
     * Processa pedido (mudança de status)
     */
    public Order processOrder(Long id) {
        return updateOrderStatus(id, OrderStatus.PROCESSING);
    }

    /**
     * Marca pedido como enviado
     */
    public Order shipOrder(Long id) {
        return updateOrderStatus(id, OrderStatus.SHIPPED);
    }

    /**
     * Marca pedido como entregue
     */
    public Order deliverOrder(Long id) {
        return updateOrderStatus(id, OrderStatus.DELIVERED);
    }

    /**
     * Atualiza status do pedido
     */
    private Order updateOrderStatus(Long id, OrderStatus newStatus) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(newStatus);
                    order.onUpdate();
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }
}