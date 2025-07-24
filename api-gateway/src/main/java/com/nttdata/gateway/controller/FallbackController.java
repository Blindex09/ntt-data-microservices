package com.nttdata.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller para fallback quando serviços estão indisponíveis
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> productFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product Service está temporariamente indisponível");
        response.put("status", "SERVICE_UNAVAILABLE");
        response.put("timestamp", LocalDateTime.now());
        response.put("suggestion", "Tente novamente em alguns momentos");
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> orderFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order Service está temporariamente indisponível");
        response.put("status", "SERVICE_UNAVAILABLE");
        response.put("timestamp", LocalDateTime.now());
        response.put("suggestion", "Tente novamente em alguns momentos");
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "API Gateway");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "API Gateway está funcionando corretamente");
        
        return ResponseEntity.ok(response);
    }
}