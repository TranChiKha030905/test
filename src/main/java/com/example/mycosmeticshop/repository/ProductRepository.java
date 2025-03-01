package com.example.mycosmeticshop.repository;

import com.example.mycosmeticshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}