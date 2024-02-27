package com.vmoscalciuc.apigateway.config.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;

    public AuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = (String) authentication.getCredentials(); // Cast to String directly

        String username = null;
        Claims claims = null;

        try {
            username = jwtUtil.extractUsername(authToken);
            claims = jwtUtil.getClaimsFromToken(authToken); // Get claims for authority extraction
        } catch (Exception e) {
            System.out.println(e);
        }

        if (username != null && jwtUtil.validateToken(authToken)) {
            List<String> role = claims.get("role", List.class);
            List<SimpleGrantedAuthority> authorities = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            // Use a token type suitable for holding JWTs, e.g., PreAuthenticatedAuthenticationToken:
            Authentication authenticationToken = new PreAuthenticatedAuthenticationToken(username, authToken, authorities);

            return Mono.just(authenticationToken);
        } else {
            return Mono.empty();
        }
    }
}
