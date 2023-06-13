package com.samiak.azuredatabricks;

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class AzuredatabricksApplication {

    public static void main(String[] args) {
        SpringApplication.run(AzuredatabricksApplication.class, args);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.apply(AadWebApplicationHttpSecurityConfigurer.aadWebApplication())
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated();
        return http.build();
    }

}
