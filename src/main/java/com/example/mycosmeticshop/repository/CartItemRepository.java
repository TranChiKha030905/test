package com.example.mycosmeticshop.repository;

import com.example.mycosmeticshop.entity.CartItem;
import com.example.mycosmeticshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}