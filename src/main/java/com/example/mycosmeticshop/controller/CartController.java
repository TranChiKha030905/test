package com.example.mycosmeticshop.controller;

import com.example.mycosmeticshop.entity.CartItem;
import com.example.mycosmeticshop.entity.Product;
import com.example.mycosmeticshop.entity.User;
import com.example.mycosmeticshop.repository.CartItemRepository;
import com.example.mycosmeticshop.repository.ProductRepository;
import com.example.mycosmeticshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItemRepository.save(cartItem);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("cartItems", cartItemRepository.findByUser(user));
        return "cart";
    }
}