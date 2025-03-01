package com.example.mycosmeticshop.repository;

import com.example.mycosmeticshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}