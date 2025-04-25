package com.todo.api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwt: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION).orEmpty()
        val authorization = header.split(" ")
        if (authorization.first() == "Bearer" && authorization.size > 1) {
            runCatching { jwt.parse(authorization[1]) }
                .onSuccess { payload ->
                    val auth =
                        UsernamePasswordAuthenticationToken(payload, null, emptyList())
                    SecurityContextHolder.getContext().authentication = auth
                }
                .onFailure {
                    logger.error("Failed parse token with error: ${it.message}")
                }
        }

        filterChain.doFilter(request, response)
    }
}