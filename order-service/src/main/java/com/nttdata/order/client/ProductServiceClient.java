package com.nttdata.order.client;

import com.nttdata.order.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Feign Client para comunicação com o Product Service
 */
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    /**
     * Busca produto por ID
     */
    @GetMapping("/products/{id}")
    ProductDTO getProduct(@PathVariable("id") Long id);

    /**
     * Verifica se há estoque disponível
     */
    @GetMapping("/products/{id}/stock/{quantity}")
    Boolean checkStock(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity);

    /**
     * Reduz estoque do produto
     */
    @PutMapping("/products/{id}/reduce-stock/{quantity}")
    Boolean reduceStock(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity);
}