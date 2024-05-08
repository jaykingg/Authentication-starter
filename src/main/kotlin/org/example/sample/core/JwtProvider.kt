package org.example.sample.core

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class JwtProvider(private val jwtUtil: JwtUtil) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()
        val username = jwtUtil.extractUsername(authToken)
        return if (username != null && !jwtUtil.isTokenExpired(authToken)) {
            Mono.just(UsernamePasswordAuthenticationToken(username, null, emptyList()))
        } else Mono.empty()
    }
}