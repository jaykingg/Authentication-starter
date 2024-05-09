package org.example.sample.config

import org.example.sample.core.JwtProvider
import org.example.sample.core.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        val jwtAuthenticationManager = JwtProvider(JwtUtil())
        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeExchange { exchange ->
                exchange
                    .pathMatchers("/api/auth/register").permitAll()
                    .pathMatchers("/api/auth/token").permitAll()
                    .pathMatchers("/swagger-ui/**").permitAll()
                    .pathMatchers("/swagger**").permitAll()
                    .pathMatchers("/webjars/**").permitAll()
                    .pathMatchers("/api-docs/**").permitAll()
                    .pathMatchers("/swagger.html").permitAll()
                    .anyExchange().authenticated()
            }
            .authenticationManager(jwtAuthenticationManager)
            .build()
    }
}