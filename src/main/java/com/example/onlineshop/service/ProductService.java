package com.example.onlineshop.service;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CurrencyConverterService currencyConverterService;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + id));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCurrency(productDetails.getCurrency());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + id));
        productRepository.delete(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + id));
    }

    public BigDecimal convertProductPrice(Long productId, String targetCurrency){
        Product product = getProductById(productId);
        BigDecimal price = BigDecimal.valueOf(product.getPrice());
        logger.debug("Original price: {}", price);
        BigDecimal convertedPrice = currencyConverterService.convertCurrency(product.getCurrency(), targetCurrency, price);
        logger.debug("Converted price: {}", convertedPrice);
        return convertedPrice;
    }

}
