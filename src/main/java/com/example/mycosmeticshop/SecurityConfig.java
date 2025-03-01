package com.example.mycosmeticshop;

import com.example.mycosmeticshop.entity.User;
import com.example.mycosmeticshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("Attempting to find user: " + username);
            User user = userRepository.findByUsername(username);
            System.out.println("User found: " + (user != null ? user.getUsername() : "null"));
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            String role = user.getRole().startsWith("ROLE_") ? user.getRole().substring(5) : user.getRole();
            System.out.println("Role after processing: " + role);
            return User
                    .withUsername(user.getUsername()) // Sử dụng static method
                    .password(user.getPassword())
                    .roles(role)
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/cart/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            System.out.println("Login successful for user: " + authentication.getName());
                            response.sendRedirect("/");
                        })
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }
}