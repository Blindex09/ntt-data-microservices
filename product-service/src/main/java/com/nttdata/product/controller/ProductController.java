package com.nttdata.product.controller;

import com.nttdata.product.model.Product;
import com.nttdata.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciamento de produtos
 */
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Lista todos os produtos
     * GET /products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * Busca produto por ID
     * GET /products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo produto
     * POST /products
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        try {
            Product savedProduct = productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualiza produto
     * PUT /products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.update(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta produto
     * DELETE /products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca produtos por nome
     * GET /products/search?name=valor
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("name") String name) {
        List<Product> products = productService.findByName(name);
        return ResponseEntity.ok(products);
    }

    /**
     * Lista produtos em estoque
     * GET /products/in-stock
     */
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> products = productService.findInStock();
        return ResponseEntity.ok(products);
    }

    /**
     * Busca produtos por faixa de preço
     * GET /products/price-range?min=10&max=100
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam("min") BigDecimal min,
            @RequestParam("max") BigDecimal max) {
        List<Product> products = productService.findByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }

    /**
     * Verifica estoque disponível (usado internamente pelo Order Service)
     * GET /products/{id}/stock/{quantity}
     */
    @GetMapping("/{id}/stock/{quantity}")
    public ResponseEntity<Boolean> checkStock(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
        boolean hasStock = productService.hasStock(id, quantity);
        return ResponseEntity.ok(hasStock);
    }

    /**
     * Reduz estoque (usado internamente pelo Order Service)
     * PUT /products/{id}/reduce-stock/{quantity}
     */
    @PutMapping("/{id}/reduce-stock/{quantity}")
    public ResponseEntity<Boolean> reduceStock(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
        boolean success = productService.reduceStock(id, quantity);
        return ResponseEntity.ok(success);
    }
}