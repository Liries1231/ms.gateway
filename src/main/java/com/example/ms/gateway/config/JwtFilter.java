package com.example.ms.gateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
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
    @Value("${user.service.url}")
    private String userServiceUrl; // URL микросервиса ms-users
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
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
        String userServiceUri = UriComponentsBuilder.fromHttpUrl(userServiceUrl)
                .path("/validate-token")
                .queryParam("token", token)
                .toUriString();
        log.info("User service URI: {}", userServiceUri);
        return webClientBuilder.build()
                .get()
                .uri(userServiceUri)
                .retrieve()
                .bodyToMono(User.class)
                .flatMap(user -> {
                    if (user == null) {
                        log.error("User service returned null user for token validation");
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user token"));
                    }
                    return chain.filter(exchange);
                })
                .onErrorResume(WebClientResponseException.Unauthorized.class, e -> {
                    log.error("Unauthorized error when validating token");
                    return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Error validating token in user service", e);
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error validating token in user service"));
                });
    }
}