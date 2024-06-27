package com.example.onlineshop.controllers;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/products") // This is the base path for all the methods in this class
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProduct(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product-detail";
    }

    @GetMapping("/new")
    public String showNewProductForm(Model model){
        model.addAttribute("product", new Product());
        return "product-form";
    }


    @PostMapping
    public String saveProduct(@ModelAttribute Product product){
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product productDetails){
        productService.updateProduct(id, productDetails);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

// (Model model) is used to add attributes to the model object. The model object is used to pass data to the view. -> Often used with @GetMapping
// @ModelAttribute is used to bind the form submission parameters to the object. -> Often used with @PostMapping
