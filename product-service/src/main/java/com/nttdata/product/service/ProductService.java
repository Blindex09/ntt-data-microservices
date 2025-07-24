package com.nttdata.product.service;

import com.nttdata.product.model.Product;
import com.nttdata.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service para regras de negócio de produtos
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Lista todos os produtos
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Busca produto por ID
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Salva um novo produto
     */
    public Product save(Product product) {
        // Validação de negócio: não permite produtos duplicados
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Produto com este nome já existe");
        }
        return productRepository.save(product);
    }

    /**
     * Atualiza um produto
     */
    public Product update(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setStock(productDetails.getStock());
                    product.onUpdate();
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    /**
     * Deleta um produto
     */
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Busca produtos por nome
     */
    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Busca produtos em estoque
     */
    public List<Product> findInStock() {
        return productRepository.findByStockGreaterThan(0);
    }

    /**
     * Busca produtos por faixa de preço
     */
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Reduz estoque do produto (usado pelo Order Service)
     */
    public boolean reduceStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                product.onUpdate();
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se tem estoque disponível
     */
    public boolean hasStock(Long productId, Integer quantity) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(p -> p.getStock() >= quantity).orElse(false);
    }
}