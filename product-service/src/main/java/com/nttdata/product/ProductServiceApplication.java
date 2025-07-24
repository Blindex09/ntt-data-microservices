package com.nttdata.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Product Service - Microserviço de Catálogo de Produtos
 * 
 * Responsável pelo gerenciamento de produtos no sistema.
 * Inclui operações CRUD para produtos com persistência em banco H2.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}