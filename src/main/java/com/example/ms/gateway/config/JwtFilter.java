package com.example.ms.gateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ms.gateway.dto.User;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.Optional;
@Component
@Slf4j
public class JwtFilter implements WebFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String uri = exchange.getRequest().getURI().toString();
        log.info("Received request to URI: {}", uri);

        if (uri.contains("/login") || uri.contains("/register")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.error("Authorization header is missing or invalid");
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing or invalid"));
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        log.info("Token received: {}", token);

        // Декодирование токена и извлечение userId
        String userId = extractUserIdFromToken(token);
        if (userId == null) {
            log.error("UserId is null in token");
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
        }

        log.info("Extracted userId from token: {}", userId);

        exchange.getRequest().mutate()
                .header("X-User-Id", userId)
                .build();

        return chain.filter(exchange);
    }

    private String extractUserIdFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Long userId = decodedJWT.getClaim("userId").asLong();
            return userId != null ? userId.toString() : null;
        } catch (Exception e) {
            log.error("Error decoding token", e);
            return null;
        }
    }
}