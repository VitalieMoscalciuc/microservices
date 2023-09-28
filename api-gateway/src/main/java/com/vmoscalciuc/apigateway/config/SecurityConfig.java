package com.vmoscalciuc.apigateway.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.reactive.*;
import org.springframework.security.config.web.server.*;
import org.springframework.security.core.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity
                .csrf().disable()
                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
                return serverHttpSecurity.build();
    }

//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return ReactiveJwtDecoders.fromOidcIssuerLocation(issuerUri);
//    }
}
