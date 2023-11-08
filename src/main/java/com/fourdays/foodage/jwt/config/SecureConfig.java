package com.fourdays.foodage.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

public class SecureConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)


                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/hello")
                        .permitAll()

                )
                .authorizeHttpRequests(request -> request.anyRequest().authenticated());
        return http.build();
    }
}
