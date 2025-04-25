package com.todo.api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration-hours}") private val expirationTime: Long,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun generate(userId: Long): String {
        val token = Jwts.builder()
            .subject("$userId")
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plus(expirationTime, ChronoUnit.HOURS)))
            .signWith(key)
            .compact()

        return token
    }

    fun parse(token: String): Long {
        val tokenParser = Jwts.parser().verifyWith(key).build()
        val claims = tokenParser.parseSignedClaims(token).payload

        return claims.subject.toLong()
    }
}