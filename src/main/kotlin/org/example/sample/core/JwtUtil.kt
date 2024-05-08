package org.example.sample.core

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(private val secretKey: String = "b8bfa9b8435613e425f43aec82f0654daf5f4dfc457ade485e2dd76662d746d3f0b457f9b869f6ed576865ff9e4b29cf0cd97890ab3456c6457d83d7576f29b6") {

    fun generateToken(username: String): String = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact()

    fun extractClaims(token: String): Claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .body

    fun extractUsername(token: String): String? = extractClaims(token).subject

    fun isTokenExpired(token: String): Boolean = extractClaims(token).expiration.before(Date())
}
