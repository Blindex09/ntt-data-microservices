package com.nttdata.order.model;

/**
 * Enum para status do pedido
 */
public enum OrderStatus {
    PENDING("Pendente"),
    CONFIRMED("Confirmado"),
    PROCESSING("Processando"),
    SHIPPED("Enviado"),
    DELIVERED("Entregue"),
    CANCELLED("Cancelado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}