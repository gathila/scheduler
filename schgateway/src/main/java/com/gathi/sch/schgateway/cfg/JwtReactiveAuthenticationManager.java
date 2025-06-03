package com.gathi.sch.schgateway.cfg;

import com.gathi.sch.schgateway.svc.JWTService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.util.List;


@Configuration
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTService jwtService;

    public JwtReactiveAuthenticationManager(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        if (!jwtService.isTokenValid(token)) {
            return Mono.error(new BadCredentialsException("Invalid JWT token"));
        }

        String username = jwtService.extractEmail(token);
        return Mono.just(new UsernamePasswordAuthenticationToken(username, null, List.of()));
    }
}
