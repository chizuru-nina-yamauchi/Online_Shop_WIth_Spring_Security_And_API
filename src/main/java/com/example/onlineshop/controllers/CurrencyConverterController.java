package com.example.onlineshop.controllers;

import com.example.onlineshop.service.CurrencyConverterService;
import com.example.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CurrencyConverterController {
    @Autowired// Dependency Injection
    private ProductService productService;

    @GetMapping("/convert-price")
    public String showCurrencyConverter(@RequestParam Long productId, @RequestParam String targetCurrency, Model model) {
        BigDecimal convertedPrice = productService.convertProductPrice(productId, targetCurrency);
        model.addAttribute("convertedPrice", convertedPrice); // Passing the converted Price to the view. This makes available the convertedPrice to the Thymeleaf view(HTML file).
        return "currency-converter";
    }


    @GetMapping("/convert-price-form")
    public String showCurrencyConverterForm(@RequestParam Long productId, Model model) {
        model.addAttribute("productId", productId);
        return "currency-converter";
    }
}
// We don't have to create @Postmapping because we are not changing anything in the database. We are just converting the currency.
