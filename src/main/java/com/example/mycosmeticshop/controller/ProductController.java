package com.example.mycosmeticshop.controller;

import com.example.mycosmeticshop.entity.Product;
import com.example.mycosmeticshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        System.out.println("User authenticated: " + (authentication != null ? authentication.getName() : "none"));
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/admin/add-product")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/admin/add-product")
    public String addProduct(Product product) {
        productRepository.save(product);
        return "redirect:/";
    }
}